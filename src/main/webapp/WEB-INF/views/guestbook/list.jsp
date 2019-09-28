<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite3</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		
		<div id="content">
			<div id="guestbook">
			
				<form action="${pageContext.servletContext.contextPath }/guestbook/add" method="post">
					<table border=1 width=500>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="contents" cols=60 rows=5></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>

				
				<c:set var="count" value= '${fn:length(list) }' />
				<ul>
					<c:forEach items='${list }' var='guestbookVo' varStatus='status'>
						<br>
						<table width=510 border=1>
							<tr>
								<td>[${count - status.index}]</td>
								<td>${guestbookVo.name}</td>
								<td>${guestbookVo.regDate}</td>
								<td><a href="${pageContext.servletContext.contextPath }/guestbook/deleteform/${guestbookVo.no}">삭제</a></td>
							</tr>
							<tr> 
								<td colspan=4>${fn:replace(guestbookVo.contents, newline, '<br>') }
								</td>
							</tr>
						</table>
					</c:forEach>
				</ul>

			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>