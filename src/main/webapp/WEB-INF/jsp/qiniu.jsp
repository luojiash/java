<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="http://upload.qiniu.com/" enctype="multipart/form-data">
	  <!-- <input name="key" type="hidden" value="<resource_key>"> -->
	  <%-- <input name="x:<custom_name>" type="hidden" value="<custom_value>"> --%>
	  <input name="token" type="hidden" value="SbhOkgYtnMGL5xFu8DJsp2bCa2u_JMghgU18AJ8Z:rJYVM9LqNkcKPD5o5OlR6BmcVQ8=:eyJpbnNlcnRPbmx5IjoxLCJzY29wZSI6ImludGVybmFsIiwiY2FsbGJhY2tVcmwiOiIiLCJkZWFkbGluZSI6MTQ1OTMxOTExMiwiY2FsbGJhY2tCb2R5Ijoia2V5XHUwMDNkJChrZXkpXHUwMDI2aGFzaFx1MDAzZCQoZXRhZykifQ==">
	  <input name="file" type="file" />
	  <!-- <input name="crc32" type="hidden" />
	  <input name="accept" type="hidden" /> -->
	  <input type="submit" value="上传">
	</form>
</body>
</html>