<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>EnjoyTrip</title>
  
  <!-- CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>

  <!-- JS -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"
          integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <%-- header.jsp include --%>
  <jsp:include page="/WEB-INF/views/components/header.jsp" />
<c:if test="${not empty msg}">
  <div class="toast-container position-fixed top-0 start-50 translate-middle-x p-3">
    <div class="toast align-items-center text-white bg-success border-0 show" role="alert">
      <div class="d-flex">
        <div class="toast-body">
          ${msg}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    </div>
  </div>
</c:if>


  <main class="content">
    <%-- 그대로 유지된 HTML 구조 --%>
    <section class="jumbotron">
      <article class="top-right">
        <h4>
          지금
          <span style="font-weight: bold; color: orange">Enjoy!!!Trip</span>과
          함께 우리지역의 관광지를 알아보고<br />나만의 여행 계획을
          만들어보세요!!!
        </h4>
        <div>
          <p class="explane">나만의 여행 계획을 짜보고 공유해 보세요.</p>
          <button type="button" class="btn btn-primary">일정 더보기</button>
        </div>
      </article>
      <form class="ask">
        <h3 class="title">문의하기!</h3>
        <p class="text-gray">이름과 연락처를 남겨주세요.</p>
        <input type="text" class="form-control" placeholder="이름" />
        <input type="tel" class="form-control" placeholder="전화번호" />
        <div>
          <input type="checkbox" class="form-check-input" name="agree" id="info-provide-agree" />
          <label for="info-provide-agree" class="form-check-label text-gray">정보제공동의함</label>
        </div>
        <button type="submit" class="btn btn-primary">보내기</button>
      </form>
    </section>

    <section class="middle">
        <article class="middle-img">
          <div class="img img1">해변</div>
          <div class="img img2">해변</div>
          <div class="img img3">해변</div>
        </article>
        <div>
          <p>지역사랑!</p>
          <h3>우리지역 관광지.</h3>
          <p>우리지역의 숨어있는 아름다운 관광지를 알려드립니다.</p>
        </div>
      </section>
      <section class="myplan">
        <h2 class="title">나만의 여행계획!!</h2>
        <div class="line-bar"></div>
        <p class="explane">
          여행 경로, 숙박, 예상금액들 나만의 멋진 계획을 세워 공유해 주세요!!!
        </p>
        <div id="carouselExampleIndicators" class="carousel slide">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <div class="item">
                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_1.png"
                    class="card-img-top"
                    alt="신혼여행코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">신혼여행코스</h5>
                    <p class="card-text">
                      위 코스를 따라가면 어떨까요? 조만간 부부로 떠나요~
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>
                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_2.png"
                    class="card-img-top"
                    alt="심심하신분 모이세요"
                  />
                  <div class="card-body">
                    <h5 class="card-title">심심하신분 모이세요</h5>
                    <p class="card-text">
                      가볍게 근교로 놀러가요! 우리나라 숨겨진 아름다운 명소들
                      다녀보세요~
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_3.png"
                    class="card-img-top"
                    alt="군대투어"
                  />
                  <div class="card-body">
                    <h5 class="card-title">군대투어</h5>
                    <p class="card-text">
                      본인이 다녀온 군부대를 중심으로 돌아다니는 군대여행~
                      다녀오신 분들은 공감 백배!
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_4.png"
                    class="card-img-top"
                    alt="심심하면 떠나자!!"
                  />
                  <div class="card-body">
                    <h5 class="card-title">심심하면 떠나자!!</h5>
                    <p class="card-text">
                      가볍게 근교로 놀러가요! 우리나라 숨겨진 아름다운 명소들
                      다녀보세요~
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>
              </div>
            </div>
            <div class="carousel-item">
              <div class="item">
                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_1.png"
                    class="card-img-top"
                    alt="힐링 여행 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">힐링 여행 코스</h5>
                    <p class="card-text">
                      자연 속에서 힐링하며 스트레스를 날려버리세요! 추천
                      코스입니다.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_2.png"
                    class="card-img-top"
                    alt="맛집 탐방 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">맛집 탐방 코스</h5>
                    <p class="card-text">
                      전국의 숨겨진 맛집을 찾아 떠나는 여행! 먹방 여행을
                      즐겨보세요.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_3.png"
                    class="card-img-top"
                    alt="캠핑 코스 추천"
                  />
                  <div class="card-body">
                    <h5 class="card-title">캠핑 코스 추천</h5>
                    <p class="card-text">
                      자연과 함께하는 캠핑 여행! 가족, 친구와 함께 떠나보세요.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_4.png"
                    class="card-img-top"
                    alt="역사 탐방 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">역사 탐방 코스</h5>
                    <p class="card-text">
                      우리나라의 역사를 배우며 여행하는 특별한 시간! 추천합니다.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>
              </div>
            </div>
            <div class="carousel-item">
              <div class="item">
                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_1.png"
                    class="card-img-top"
                    alt="문화유산 탐방 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">문화유산 탐방 코스</h5>
                    <p class="card-text">
                      우리나라의 전통과 역사를 느낄 수 있는 문화유산 여행을
                      떠나보세요.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_2.png"
                    class="card-img-top"
                    alt="호캉스 추천 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">호캉스 추천 코스</h5>
                    <p class="card-text">
                      도심 속에서 즐기는 럭셔리한 호캉스! 완벽한 휴식을 원한다면
                      이곳으로.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_3.png"
                    class="card-img-top"
                    alt="계곡 여행 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">계곡 여행 코스</h5>
                    <p class="card-text">
                      시원한 계곡에서 자연과 함께 여유로운 시간을 보내보세요.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>

                <div class="card" style="width: 18rem">
                  <img
                    src="./assets/img/plan_4.png"
                    class="card-img-top"
                    alt="겨울 눈꽃 여행 코스"
                  />
                  <div class="card-body">
                    <h5 class="card-title">겨울 눈꽃 여행 코스</h5>
                    <p class="card-text">
                      겨울의 낭만을 만끽할 수 있는 눈꽃 여행지! 사진 찍기 좋은
                      명소입니다.
                    </p>
                    <a href="#" class="btn btn-primary">더보기...</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="carousel-indicators">
            <button
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="0"
              class="carousel-btn active"
              aria-current="true"
              aria-label="Slide 1"
            ></button>
            <button
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="1"
              aria-label="Slide 2"
              class="carousel-btn"
            ></button>
            <button
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="2"
              aria-label="Slide 3"
              class="carousel-btn"
            ></button>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </section>

  </main>

  <%-- footer.jsp include --%>
  <jsp:include page="components/footer.jsp" />

  <!-- JS Scripts -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
          crossorigin="anonymous"></script>
  <script src="./assets/js/keys.js"></script>
  <script src="./assets/js/common.js"></script>
  <script src="./assets/js/enjoytrip.js"></script>

  <script>
    $(document).ready(function () {
      initializeAreaCode();
    });
  </script>
</body>
</html>
