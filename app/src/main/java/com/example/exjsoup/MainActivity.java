package com.example.exjsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 소녀나라 홈페이지에서 검색한 내용을 가져오는 App
 * 제품명과 제품의 링크를 가져옴
 */
public class MainActivity extends AppCompatActivity {


    private String url = "https://sonyunara.com/shop/search.php?searchOrder=&keyword="; // 검색 키워드는 빈칸으로 보냄
    private String htmlContentInStringFormat;
    EditText keyword;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyword = (EditText)findViewById(R.id.searchKey);
        tv = (TextView)findViewById(R.id.data);
        btn = (Button)findViewById(R.id.button);


        tv.setMovementMethod(new ScrollingMovementMethod());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.JsoupAsyncTask jsoupAsyncTask = new MainActivity.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // EditText에 입력한 글자를 가져와서 url에 추가해줌
                String findkey = keyword.getText().toString();
                url += findkey;
                // 웹에서 내용을 가져옴
                Document doc = Jsoup.connect(url).get();
                // 내용 중 원하는 부분을 가져옴
                // class 명이 subject 인 element 의 아래에 있는 a element 의 href 속성 값 가져옴 | 제품 명 가져옴
                Elements subject = doc.select(".subject a[href]");
                // attr() : 기본적으로 String 형 | 제품의 링크를 가져옴
                String subjectLink = subject.attr("abs:href");
                for (Element link : subject) { // for 문을 활용해서 모든 항목을 가져옴
                    // 원하는 부분은 element 형태이므로 String 형태로 바꿔줌
                    System.out.println("data : " + link.text()); // terminal 에 찍음
                    // 앱 화면에 찍을 내용을 변수에 저장
                    htmlContentInStringFormat += link.text().trim() + "\n" + subjectLink + "\n-------------------------------------\n";
                }
            } catch (IOException e) { // Jsoup 의 connect 부분에서 오류 발생 가능성이 있으므로 예외 처리
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            tv.setVisibility(View.VISIBLE); // TextView를 gone으로 해두었기 때문에 visible로 바꿔줘야함
            tv.setText(htmlContentInStringFormat);
        }

    }

}