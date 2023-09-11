function getUrlParam(paraName) {
    var url = document.location.toString();
    //alert(url);
    var arrObj = url.split("?");  // [http://localhost:8080/xxx,name=aaa&password=123]
    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&"); //[name=aaa,password=123]
        var arr;
        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("="); //[name,aaa]  [password,123]
            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}