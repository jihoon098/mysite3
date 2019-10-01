<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
			
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
				
					
					<!-- 게시판에 있는 글 보여주기-->				
					<c:choose>
						<c:when test='${fn:length(list) <= page*10}'>
							<c:set var = "count" value = '${fn:length(list) }'/>
						</c:when>
						<c:otherwise>
							<c:set var = "count" value = '${page*10 }'/>
						</c:otherwise>
					</c:choose>

					<c:forEach items = '${list }' var = 'boardVo' begin='${page*10-10 }' end='${count-1 }' step='1' varStatus='status'>
						<tr>
							<td>${count - status.index }</td>

							<c:choose>
								<c:when test='${boardVo.oNo == 1}'>
									<td style="text-align:left"><a href="${pageContext.servletContext.contextPath}/board/view/${boardVo.no}">${boardVo.title }</a></td>
								</c:when>
								<c:otherwise>
									<td class="label" style="padding-left:${30*boardVo.depth-20}px;text-align:left">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" style='padding-left:${40*boardVo.depth}px'/>
									<a href="${pageContext.servletContext.contextPath}/board/view/${boardVo.no}">${boardVo.title }</a></td>
								</c:otherwise>
							</c:choose>

							
							<td>${boardVo.userName }</td>
							<td>${boardVo.hit }</td>
							<td>${boardVo.regDate }</td>

							<c:choose>
								<c:when test='${empty authUser}'>
									<td><a href="" class=""></a></td>
								</c:when>
								<c:otherwise>
									<!-- authUser.no와 board.user_no가 같으면!!! 보여줘야함. -->
									<c:choose>
										<c:when test='${authUser.no == boardVo.userNo }'>
											<td><a href="${pageContext.servletContext.contextPath}/board/delete/${boardVo.no}" class="del">삭제</a></td>
										</c:when>
										<c:otherwise>
											<td><a href="" class=""></a></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

						</tr>
					</c:forEach>
						
					
				</table>
				
				
				<!-- pageing -->
				<!-- <c:set var = "pageCount" value = '${fn:length(list)/10 + 1 }'/> -->
				<div class="pager">
					<ul>

						<c:choose>
							<c:when test="${(page%5) eq 1 }">
								<c:set var = "pageCount" value = '${page }'/>
							</c:when>
							<c:otherwise>
								<c:set var = "pageCount" value = '${page-(page%5-1) }'/>
							</c:otherwise>
						</c:choose>
						
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${page-1 }">◀</a></li>
						
						<c:forEach var = "page" begin = '${pageCount }' end = '${fn:length(list)/10+1 }' varStatus='status'>			
							<li><a href="${pageContext.servletContext.contextPath}/board?page=${page }">${page }</a></li>
						</c:forEach>

						<li><a href="${pageContext.servletContext.contextPath}/board?page=${page+1 }">▶</a></li>
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test='${!empty authUser}'>
						<a href="${pageContext.servletContext.contextPath }/board/writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>
			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>