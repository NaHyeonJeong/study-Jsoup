package com.example.exjsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    /**
     * 소녀나라 사이트에서 150이라는 키워드가 붙은 제품명을 모두 가져옴
     * 사이트에서 미리 150을 검색한 페이지 URL 을 가져옴
     */
    private String url = "https://sonyunara.com/shop/search.php?searchOrder=&keyword=150";
    private String htmlContentInStringFormat;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.data);
        btn = (Button)findViewById(R.id.button);

        tv.setMovementMethod(new ScrollingMovementMethod());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
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
                // 웹에서 내용을 가져옴
                Document doc = Jsoup.connect(url).get();
                // 내용 중 원하는 부분을 가져옴
                Elements links = doc.select(".subject"); // class 명이 subject 인 항목을 가져옴
                for (Element link : links) { // for 문을 활용해서 모든 항목을 가져옴
                    // 원하는 부분은 element 형태이므로 String 형태로 바꿔줌
                    System.out.println("data : " + link.text());
                    htmlContentInStringFormat += link.text().trim() + "\n";
                }
            } catch (IOException e) { // Jsoup 의 connect 부분에서 오류 발생 가능성이 있으므로 예외 처리
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            tv.setText(htmlContentInStringFormat);
        }

    }
}