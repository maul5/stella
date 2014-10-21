/**
 * jsp 파일을 바로 찾아 가고자 할 때 사용함
 * 
 * @param path
 * @param file
 * @param param
 */
function direct_view(path, file, param) {
	$("input[name=param]").val(JSON.stringify(param));
	$("input[name=path]").val("/" + path + "/" + file);
	$("#frmNav").attr({
		action : "/stella/direct_view.do",
		target : "_self"
	});

	$("#frmNav").submit();
}

/**
 * 문자열을 원하는 포맷 형태로 변환
 */
function setFormat(sData, sMask) {
	var resData = "";
	var i = 0;
	var j = 0;

	if (sData == "")
		return sData;

	for (i = 0; i < sData.length; i++) {
		resData = resData + sData.charAt(i);
		j++;

		if (j < sMask.length && sMask.charAt(j) != "#") {
			resData = resData + sMask.charAt(j++);
		}
	}

	return resData;
}

/**
 * 영문자, 숫자 키보드 입력 체크
 */
function chkAlphaNumKey() {
	if (!((event.keyCode >= 48 && event.keyCode <= 57) || // 숫자
	(event.keyCode >= 65 && event.keyCode <= 90) || // 대문자
	(event.keyCode >= 97 && event.keyCode <= 122) || // 소문자
	(event.keyCode == 8) // 백스페이스
	)) {
		event.returnValue = false;
	}
}

/**
 * 현재 년을 구함(YYYY)
 */
function getYear() {
	var nDate = new Date();

	var _year = nDate.getFullYear();

	return String(_year);
}

/**
 * 현재 월을 구함(MM)
 */
function getMonth() {
	var nDate = new Date();

	var _month = nDate.getMonth() + 1;

	if (String(_month).length == 1) {
		_month = "0" + String(_month);
	}

	return String(_month);
}

/**
 * 현재 일자를 구함(DD)
 */
function getDay() {
	var nDate = new Date();

	var _date = nDate.getDate();

	if (String(_date).length == 1) {
		_date = "0" + String(_date);
	}

	return String(_date);
}

/**
 * 오늘 날짜를 구함
 */
function getToDay() {
	var nDate = new Date();

	var _year = nDate.getFullYear();
	var _month = nDate.getMonth() + 1;
	var _date = nDate.getDate();

	if (String(_month).length == 1) {
		_month = "0" + String(_month);
	}

	if (String(_date).length == 1) {
		_date = "0" + String(_date);
	}

	return String(_year) + String(_month) + String(_date);
}

/**
 * 오늘 날짜에 기정 날짜를 더함
 */
function addDate(pDate) {
	var nDate = new Date();
	var rDate = new Date(Date.parse(nDate) + (pDate * 1000 * 60 * 60 * 24));

	var _year = rDate.getFullYear();
	var _month = rDate.getMonth() + 1;
	var _date = rDate.getDate();

	if (String(_month).length == 1) {
		_month = "0" + String(_month);
	}

	if (String(_date).length == 1) {
		_date = "0" + String(_date);
	}

	return String(_year) + String(_month) + String(_date);
}

/**
 * 지정 날짜를 기준으로 전후 일자 구하기
 */
function chgDate(cDate, pDate) {
	var sailingTime = pDate;

	var yy = parseInt(cDate.replace(/\-/g, '').substring(0, 4));
	var mm = parseInt(cDate.replace(/\-/g, '').substring(4, 6));
	var dd = parseInt(cDate.replace(/\-/g, '').substring(6, 8));

	d = new Date(yy, mm - 1, parseInt(dd) + parseInt(sailingTime));

	yy = d.getFullYear();
	mm = d.getMonth() + 1;
	mm = (mm < 10) ? '0' + mm : mm;
	dd = d.getDate();
	dd = (dd < 10) ? '0' + dd : dd;

	var sDate = String(yy) + String(mm) + String(dd);

	return sDate;
}

/**
 * 지정 날짜를 기준으로 전후 년도 구하기
 */
function chgYear(cDate, pYear) {
	var yy = parseInt(cDate.replace(/\-/g, '').substring(0, 4));
	var mm = parseInt(cDate.replace(/\-/g, '').substring(4, 6));
	var dd = parseInt(cDate.replace(/\-/g, '').substring(6, 8));

	d = new Date(yy + parseInt(pYear), mm - 1, dd); // 년도를 더하고
	d = new Date(d - (1 * 1000 * 60 * 60 * 24)); // 하루를 빼고

	yy = d.getFullYear();
	mm = d.getMonth() + 1;
	mm = (mm < 10) ? '0' + mm : mm;
	dd = d.getDate();
	dd = (dd < 10) ? '0' + dd : dd;

	var sDate = String(yy) + String(mm) + String(dd);

	return sDate;
}

/**
 * 날자범위내의 Week Day를 Count 함
 */
function getWeekDays(startDate, endDate) {
	var result = 0;

	var currentDate = startDate;

	while (currentDate <= endDate) {
		var weekDay = currentDate.getDay();

		if (weekDay == 0 || weekDay == 6) {
			result++;
		}

		currentDate.setDate(currentDate.getDate() + 1);
	}

	return result;
}

/**
 * 시작일자 > 종료일자 = false / 시작일자 <= 종료일자 = true
 */
function chkFromToDate(sDate, eDate, message) {
	if (sDate == "" && eDate == "") {
		return true;
	}

	if (sDate == "" && eDate != "") {
		alert("'시작일자'를 입력하여 주십시오.");

		return false;
	}

	if (eDate == "" && sDate != "") {
		alert("'종료일자'를 입력하여 주십시오.");

		return false;
	}

	var sDateStr = sDate.replace(/\-/g, '');
	var eDateStr = eDate.replace(/\-/g, '');

	if (sDateStr.length != 8) {
		alert("'시작일자'를 확인하여 주십시오.");

		return false;
	}

	if (eDateStr.length != 8) {
		alert("'종료일자'를 확인하여 주십시오.");

		return false;
	}

	if (sDateStr > eDateStr) {
		if (message == undefined) {
			alert("'시작일자'와 '종료일자'를 확인하여 주십시오.");
		} else {
			alert(message);
		}

		return false;
	}

	return true;
}

/**
 * 시작일자 >= 종료일자 = false / 시작일자 < 종료일자 = true
 */
function getCommonFromToDateCheck2(sDate, eDate, message) {
	if (sDate == "" && eDate == "") {
		return true;
	}

	if (sDate == "" && eDate != "") {
		alert("'시작일자'를 입력하여 주십시오.");

		return false;
	}

	if (eDate == "" && sDate != "") {
		alert("'종료일자'를 입력하여 주십시오.");

		return false;
	}

	var sDateStr = sDate.replace(/\-/g, '');
	var eDateStr = eDate.replace(/\-/g, '');

	if (sDateStr.length != 8) {
		alert("'시작일자'를 확인하여 주십시오.");

		return false;
	}

	if (eDateStr.length != 8) {
		alert("'종료일자'를 확인하여 주십시오.");

		return false;
	}

	if (sDateStr >= eDateStr) {
		if (message == undefined) {
			alert("'시작일자'와 '종료일자'를 확인하여 주십시오.");
		} else {
			alert(message);
		}

		return false;
	}

	return true;
}

