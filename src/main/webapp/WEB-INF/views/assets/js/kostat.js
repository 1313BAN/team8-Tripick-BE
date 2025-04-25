let accessToken;
const map = sop.map("map");
// marker 목록
const markers = [];
// 경계 목록
const bounds = [];

// access token 가져오기
const getAccessToken = async () => {
  try {
    const json = await getFetch("https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json", {
      consumer_key: "b1fc4afb807b4829a6c7", // 서비스 id
      consumer_secret: "fa33e648ae434ffe89fb", // 보안 key
    });
    accessToken = json.result.accessToken;
  } catch (e) {
    console.log(e);
  }
};

// 주소를 UTM-K좌표로 변환해서 반환: - json의 errCd ==-401에서 access token 확보!!
const getCoords = async (address) => {
  try {
    const json = await getFetch("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocode.json", {
      accessToken: accessToken,
      address: address,
      resultcount: 1,
    });
    if (json.errCd === -401) {
      await getAccessToken();
      return await getCoords(address);
    }
    return json.result.resultdata[0];
  } catch (e) {
    console.log(e);
  }
};

const updateMap = (infos) => {
  resetMarker(); // 기존 마커 초기화
  try {
	const iconBasePath = `${contextPath}/assets/img/icons/`; // contextPath 기반 경로
    for (let i = 0; i < infos.length; i++) {
      const info = infos[i];
      // contentTypeId에 따라 다른 HTML 마커 생성
      let html;
      
	  switch (Number(info.contenttypeid)) {
	          case 12: // 관광지
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}tour.png" alt="관광지" style="width:30px;">
	                  <p>관광지</p>
	                </div>
	              </div>`;
	            break;
	          case 14: // 문화시설
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}culture.png" alt="문화시설" style="width:30px;">
	                  <p>문화시설</p>
	                </div>
	              </div>`;
	            break;
	          case 15: // 축제/행사
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}festival.png" alt="축제/행사" style="width:30px;">
	                  <p>축제/행사</p>
	                </div>
	              </div>`;
	            break;
	          case 25: // 여행코스
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}course.png" alt="여행코스" style="width:30px;">
	                  <p>여행코스</p>
	                </div>
	              </div>`;
	            break;
	          case 28: // 레포츠
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}leisure.png" alt="레포츠" style="width:30px;">
	                  <p>레포츠</p>
	                </div>
	              </div>`;
	            break;
	          case 32: // 숙박
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}accommodation.png" alt="숙박" style="width:30px;">
	                  <p>숙박</p>
	                </div>
	              </div>`;
	            break;
	          case 38: // 쇼핑
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}shopping.png" alt="쇼핑" style="width:30px;">
	                  <p>쇼핑</p>
	                </div>
	              </div>`;
	            break;
	          case 39: // 음식점
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}restaurant.png" alt="음식점" style="width:30px;">
	                  <p>음식점</p>
	                </div>
	              </div>`;
	            break;
	          default: // 기타
	            html = `
	              <div style="width:100px;height:100px;z-index:10000;background-color:rgba(255,255,255,0);">
	                <div style="text-align:center;font-weight:bold;font-size:8px;width:100px;">
	                  <img src="${iconBasePath}default.png" alt="기타" style="width:30px;">
	                  <p>기타</p>
	                </div>
	              </div>`;
	            break;
	        }

      // sop.DivIcon 생성
      const icon = new sop.DivIcon({
        html,
        className: "div_icon",
        iconSize: new sop.Point(7, 7),
        iconAnchor: new sop.Point(6, 6),
        infoWindowAnchor: new sop.Point(1, -5),
      });

      // 마커 생성 및 지도에 추가
      const marker = sop.marker([info.utmk.x, info.utmk.y], { icon });
      marker.addTo(map).bindInfoWindow(info.label);

      // 커스텀 인포윈도우 내용 정의
      console.log(info)
      const infoWindowContent = `
        <div style='font-family:sans-serif; font-size:12px;'>
          <strong>${info.label}</strong><br>
          ${info.address ? `<p>${info.address}</p>` : ""}
          ${info.zipcode ? `<p>(우) ${info.zipcode}</p>` : ""}
          ${info.tel ? `<p>(전화번호) ${info.tel}</p>` : ""}
          <p>마커 클릭시 상세보기</p>
        </div>`;

      const infoWindow = sop.infoWindow();
      infoWindow.setContent(infoWindowContent);

      // 마커 클릭 시 인포윈도우 표시
	  marker.on("click", () => {
		console.log("클릭함")
	    infoWindow.setUTMK([info.utmk.x, info.utmk.y]);
	    infoWindow.openOn(map);

	    const detailBox = document.querySelector("#spot-detail");
	    if (detailBox) {
	      detailBox.innerHTML = `
	        <h4>${info.label}</h4>
	        <p><strong>주소:</strong> ${info.address || '주소 정보 없음'}</p>
	        <p><strong>전화번호:</strong> ${info.tel || '전화번호 없음'}</p>
	        <p><strong>우편번호:</strong> ${info.zipcode || '우편번호 없음'}</p>
	        ${info.firstimage ? `<img src="${info.firstimage}" alt="${info.label}" style="max-width: 100%; margin-top: 10px;">` : ''}
	      `;
	    }
      });

      // 마커와 경계 정보 저장
      markers.push(marker);
      bounds.push([info.utmk.x, info.utmk.y]);
    }

    // 경계를 기준으로 지도 중심과 줌 설정
    if (bounds.length > 1) {
      map.setView(
        map._getBoundsCenterZoom(bounds).center,
        map._getBoundsCenterZoom(bounds).zoom
      );
    } else {
      map.setView(map._getBoundsCenterZoom(bounds).center, 9);
    }
  } catch (e) {
    console.log(e);
  }
};


// 마커와 경계 초기화
const resetMarker = () => {
  markers.forEach((item) => item.remove());
  bounds.length = 0;
};
