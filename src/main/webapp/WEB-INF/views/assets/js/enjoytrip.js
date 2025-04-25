// 지역, 시군구, 컨텐츠 타입 선택 요소 (travel-information.html)
let area, sigungu, contentType;

// 회원가입 모달 내의 지역 및 시군구 선택 요소 (header.html)
let area2, sigungu2;

// 초기화 함수
function initializeAreaCode() {
    // 요소를 안전하게 가져오기
    area = document.querySelector("#select-sido");
    sigungu = document.querySelector("#select-gugun");
    contentType = document.querySelector("#select-contents");

    area2 = document.querySelector("#select-sido2");
    sigungu2 = document.querySelector("#select-gugun2");

    // travel-information.html 페이지에만 존재하는 경우
    if (area) {
        areaCode1(null, area); // 지역 데이터를 travel-information.html에 로드
        area.addEventListener("change", async () => {
            await areaCode1(area.value, sigungu);
        });
    }

    // header.html (회원가입 모달)에서만 존재하는 경우
    if (area2) {
        areaCode1(null, area2); // 회원가입 모달에 지역 데이터 로드
        area2.addEventListener("change", async () => {
            await areaCode1(area2.value, sigungu2);
        });
    }

    // 콘텐츠 타입 변경 이벤트 리스너
    if (contentType) {
        contentType.addEventListener("change", async (e) => {
            await handleContentTypeChange(e);
        });
    }
}

// 지역 및 시군구 데이터를 불러오는 함수
const areaCode1 = async (areaCode = null, targetSelect = null) => {
    const queryObj = {
        serviceKey: key_data,
        numOfRows: 120,
        pageNo: 1,
        MobileOS: "ETC",
        MobileApp: "Test",
        _type: "json",
    };

    if (areaCode) {
        queryObj.areaCode = areaCode;
    }

    try {
        const json = await getFetch(
            "https://apis.data.go.kr/B551011/KorService1/areaCode1",
            queryObj
        );
        console.log("areaCode1", json);

        const info = json.response.body.items.item.map((item) => ({
            key: item.code,
            label: item.name,
        }));

        if (areaCode && targetSelect) {
            // 시군구 업데이트 (특정 지역에 해당하는 시군구)
            updateSelect(targetSelect, "시군구", info);
        } else if (targetSelect) {
            // 지역 선택 요소 업데이트
            updateSelect(targetSelect, "지역", info);
        }
    } catch (e) {
        console.log(e);
    }
};

// 콘텐츠 타입 변경 시 데이터를 불러오는 함수
const handleContentTypeChange = async (e) => {
    const queryObj = {
        serviceKey: key_data,
        numOfRows: 20,
        pageNo: 1,
        MobileOS: "ETC",
        MobileApp: "Test",
        _type: "json",
    };

    if (area?.value) {
        queryObj.areaCode = area.value;
    }
    if (sigungu?.value) {
        queryObj.sigunguCode = sigungu.value;
    }

    let spots = [];
    try {
        if (e.target.value == "0") {
            // 모든 콘텐츠 유형을 조회
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
            queryObj.contentTypeId = e.target.value;
            const json = await getFetch(
                "https://apis.data.go.kr/B551011/KorService1/areaBasedList1",
                queryObj
            );
            spots = json.response.body.items.item || [];
        }

        spots.forEach((element) => {
            element.utmk = new sop.LatLng(element.mapy, element.mapx);
            element.address = element.addr1;
            element.label = element.title;
        });
		console.log("ㅔㅔㅔㅔ" + spots[0]);
        console.log(spots);
        updateMap(spots);

    } catch (error) {
        console.error(error);
    }
};
