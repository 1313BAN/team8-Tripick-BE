<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>지역별 관광정보</title>

  <!-- Reset CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />

  <!-- Custom CSS -->
  <link rel="stylesheet" href="./assets/css/header.css" />
  <link rel="stylesheet" href="./assets/css/footer.css" />
  <link rel="stylesheet" href="./assets/css/travel-information.css" />

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"
          integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

  <!-- SGIS 지도 API -->
  <script type="text/javascript"
          src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=b1fc4afb807b4829a6c7"></script>
</head>
<body>

  <%-- 공통 헤더 include --%>
  <jsp:include page="components/header.jsp" />

  <!-- 메인 콘텐츠 -->
  <main class="contents">
    <h3>지역별 관광정보</h3>
    <div class="line"></div>

    <!-- 지역 및 컨텐츠 선택 -->
    <div class="selects">
      <select id="select-sido" class="form-select" aria-label="시·도 선택">
        <option selected>시·도 선택</option>
      </select>

      <select id="select-gugun" class="form-select" aria-label="구·군 선택">
        <option selected>구·군 선택</option>
      </select>

      <select id="select-contents" class="form-select" aria-label="컨텐츠 선택">
        <option value="0" selected>컨텐츠선택</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </select>
    </div>

    <!-- 지도 -->
    <div id="map"></div>
  </main>

  <%-- 공통 푸터 include --%>
  <jsp:include page="components/footer.jsp" />

  <!-- JS 파일 로드 -->
  <script src="./assets/js/keys.js"></script>
  <script src="./assets/js/common.js"></script>
  <script src="./assets/js/enjoytrip.js"></script>
  <script src="./assets/js/kostat.js"></script>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
          crossorigin="anonymous"></script>

<script> const contextPath = "<%=request.getContextPath()%>"; </script>

  <!-- 지도 초기화 -->
  <script>
    const init = async () => {
      // 시도 셀렉트 초기화
      await areaCode1(null, document.querySelector("#select-sido"));

      // 지도 초기 위치 설정
      updateMap([
        {
          address: "서울특별시 강남구 테헤란로 212",
          utmk: await getCoords("서울특별시 강남구 테헤란로 212"),
          label: "멀티캠퍼스",
        },
      ]);
    };
    init();
	
    initializeAreaCode();
  </script>
</body>
</html>
