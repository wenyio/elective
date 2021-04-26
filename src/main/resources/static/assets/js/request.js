// import("/static/assets/plugins/jquery/jquery.js")

function request(url, { type, data, contentType, dataType}) {
    let res;
    $.ajax({
        //发送请求的地址
        url,
        //请求方式
        type: type || "GET",
        data: data && JSON.stringify(data),
        contentType : contentType || "application/json",
        //服务器返回的数据类型
        dataType: dataType || 'json',
        async:false,
        success: function(data){
            res = data
        },
        error: function(jqXHR){
            console.log("request error in ", url)
        }
    });
    return res
}
