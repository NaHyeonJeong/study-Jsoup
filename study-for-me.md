# Jsoup과 Android 공부
## Jsoup 사용법
HTML에 대한 기본 지식이 좀 필요했다. (다 까먹었는데 ㅎㅎ)
### 기본적으로 필요한 과정
1. jar 파일 다운로드   
Jsoup을 사용하기 위해서는 **.jar 파일이 필요**합니다. 밑의 링크로 가서 굵은 표시가 되어있는 파일을 받으세요.   
    * jar 파일 받는 곳 : <https://jsoup.org/download>   
2. 안드로이드 스튜디오에 jar 파일 넣기   
    파일을 받았다면 안드로이드 스튜디오의 해당 프로젝트에 넣는 것까지 해야 사용이 가능합니다   

    ![img](https://user-images.githubusercontent.com/59243409/108706020-60193e80-7551-11eb-907c-552c9cf8d6e1.png)

    원래라면 Android로 되어있을 건데 옆에 화살표를 누르면 Project가 뜨고 거기에서 app > libs 밑에 다운받은 jar 파일을 넣어주시면 Jsoup을 사용하기 위한 기본 준비가 끝난겁니다!

### 예시 코드 (실제 코드 입니다)   
doInBackground에는 웹에서 내용을 가져오고 보이기 위한 기초 작업을 합니다.   
onPostExecute에는 화면에 어떻게 보여줄지를 적어주면 됩니다. (setText 같은 것들!!)

```java
// 저는 Jsoup을 사용하기 위해 onCreate()에 밑의 두 줄을 추가했습니다
MainActivity.JsoupAsyncTask jsoupAsyncTask = new MainActivity.JsoupAsyncTask();
jsoupAsyncTask.execute();

// 위의 내용을 추가하고 나면 이 클래스를 추가하라고 합니다
private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> { 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected Void doInBackground(Void... voids) {
        // 웹에서 내용을 가져옴
        Document doc = Jsoup.connect(url).get(); // url은 웹사이트 주소
        // 가져온 내용 중 원하는 부분을 가져옴
        Elements subject = doc.select(".subject a"); // class 명이 subject 인 element 의 아래에 있는 a element 의 값을 가져옴 | 제품 명 가져옴
        String subjectLink = subject.attr("abs:href"); // attr() : 기본적으로 String 형 | 제품의 링크를 가져옴
        for (Element link : subject) { // for-each문을 활용해서 모든 항목을 가져옴
            // 앱 화면에 찍을 내용을 변수에 저장
            htmlContentInStringFormat += link.text().trim() + "\n"; // trim() : 문자열 앞/뒤 공백 삭제
            htmlContentInStringFormat2 += subjectLink + "\n";
        }
    }
    @Override
    protected void onPostExecute(Void result) {
        
    }
}
```
## 소녀나라 사이트에서 특정 키워드 제품 검색
원하는 키워드를 EditText에 입력하고 GO! 버튼을 누르면 해당하는 제품명과 제품 판매 링크를 보여줌
