<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
</head>
<body>
	<div class="container">
		<h1>기본 추천 페이지</h1>
			<div class="search-form">
				<select id="select-sido">
					<option value="">지역</option>
				</select> <select id="select-gugun">
					<option value="">시군구</option>
				</select> <select id="select-contents">
					<option value="0">전체</option>
					<option value="12">관광지</option>
					<option value="14">문화시설</option>
					<option value="15">행사/공연/축제</option>
					<option value="25">여행코스</option>
					<option value="28">레포츠</option>
					<option value="32">숙박</option>
					<option value="38">쇼핑</option>
					<option value="39">음식점</option>
				</select>

				<button id="search-button">검색</button>
			</div>

			<div id="results" class="results"></div>

			<div class="submit-button">
				<button id="submit-selected">선택한 관광지 저장</button>
			</div>
	</div>
	
	
	

    <div id="status-message"></div>

<!-- 기존 스크립트 파일 참조 -->
  <!-- JS 파일 로드 -->
  <script src="${pageContext.request.contextPath}/assets/js/keys.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/common.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/enjoytrip.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/kostat.js"></script>

<script>
    // 기존 초기화 함수 호출
    document.addEventListener("DOMContentLoaded", function() {
        initializeAreaCode();
        
        // 검색 버튼 이벤트 리스너 추가
        const searchButton = document.querySelector("#search-button");
        if (searchButton) {
            searchButton.addEventListener("click", searchAndSendData);
        }
    });
    
    // 관광지 검색 및 백엔드로 데이터 전송
    async function searchAndSendData() {
        const statusMessage = document.querySelector("#status-message");
        
        // 선택된 값 확인
        if (!area || !area.value) {
            alert("지역을 선택해주세요.");
            return;
        }
        
        statusMessage.innerHTML = "데이터를 검색하는 중입니다...";
        statusMessage.className = "";
        
        // API 호출을 위한 쿼리 객체 생성
        const queryObj = {
            serviceKey: key_data,
            numOfRows: 30,
            pageNo: 1,
            MobileOS: "ETC",
            MobileApp: "Test",
            _type: "json",
            areaCode: area.value
        };
        
        // 시군구 코드가 선택된 경우 추가
        if (sigungu && sigungu.value) {
            queryObj.sigunguCode = sigungu.value;
        }
        
        // 콘텐츠 타입이 선택된 경우
        const contentTypeValue = contentType ? contentType.value : "0";
        
        let spots = [];
        try {
            if (contentTypeValue === "0") {
                // 모든 콘텐츠 유형 조회
                const allContentTypes = [12, 14, 15, 25, 28, 32, 38, 39];
                for (const typeId of allContentTypes) {
                    queryObj.contentTypeId = typeId;
                    const json = await getFetch(
                        "https://apis.data.go.kr/B551011/KorService1/areaBasedList1",
                        queryObj
                    );
                    if (json.response.body.items.item) {
                        spots = spots.concat(json.response.body.items.item);
                    }
                }
            } else {
                // 특정 콘텐츠 유형만 조회
                queryObj.contentTypeId = contentTypeValue;
                const json = await getFetch(
                    "https://apis.data.go.kr/B551011/KorService1/areaBasedList1",
                    queryObj
                );
                spots = json.response.body.items.item || [];
            }
            
            // 결과를 백엔드로 전송
            if (spots.length > 0) {
                sendToBackend(spots);
            } else {
                statusMessage.innerHTML = "검색 결과가 없습니다.";
            }
            
        } catch (error) {
            console.error("관광지 검색 중 오류:", error);
            statusMessage.innerHTML = `오류가 발생했습니다: ${error.message}`;
            statusMessage.className = "error";
        }
    }
    
    // 백엔드로 데이터 전송
    function sendToBackend(spots) {
        const statusMessage = document.querySelector("#status-message");
        const spotData = spots.map(spot => ({
            id: spot.contentid,
            name: spot.title
        }));
        
        console.log("디버깅용", spotData);
        // 백엔드로 데이터 전송
        fetch('/api/recommendations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(spotData) 
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            statusMessage.innerHTML = `${spotData.length}개의 관광지 데이터가 백엔드로 전송되었습니다.`;
            statusMessage.className = "success";
            console.log("전송 결과:", data);
        })
        .catch(error => {
            console.error("데이터 전송 오류:", error);
            statusMessage.innerHTML = `데이터 전송 중 오류가 발생했습니다: ${error.message}`;
            statusMessage.className = "error";
        });
    }
</script>

 
	
</body>
</html>