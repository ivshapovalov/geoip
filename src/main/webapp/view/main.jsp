<html>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
      crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
      integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
      crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<style type="text/css">
    .header {
        padding: 15px;
        text-align: center;
        color: white;
        font-size: 40px;
        min-height: 100px;
        background: #dbdfe5;
        margin-bottom: 10px;

        background: red;
        background: -webkit-linear-gradient(steelblue, gray);
        background: -ms-linear-gradient(darkgray, yellow);
        background: linear-gradient(steelblue, gray);
    }
</style>
<title>Check IP</title>

<%--<body onload="document.form.ip.focus();">--%>

<div class="container" style="alignment: center">
    <div class="row">
        <div class="col-xs-12">
            <div class="header">Check IP</div>
        </div>
    </div>    <form:form name="form" action="" method="post">
        <br>
        <div >
            <div class="row" style="text-align: center">
                <div class="form-group">
                    <div class="col-xs-12">
                        <button type="submit" class="btn btn-success">Check ip
                        </button>
                        <button class="btn btn-success"
                                onclick="location.href=''" type="button">
                            Clear
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row" >
                <div class="form-group" >
                    <div class="col-xs-12" >
                            <textarea style="text-align: center"
                                      class="form-control"
                                      path="ip"
                                      id="ip"
                                      name="ip"
                                      typeof="submit"
                                      onkeydown="if (event.keyCode == 13
                                      ) { this.form.submit(); return false;}">
                            </textarea>
                    </div>
                </div>
            </div>
            <br>
            <div class="row" style="text-align: center">
                <c:choose>
                    <c:when test="${ip!=null}">
                        <div class="form-group">
                            <div class="col-xs-12">
                                <h2>
                                        ${ip}
                                </h2>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            <br>
            <div class="row" style="text-align: center">
                <c:choose>
                    <c:when test="${location==null}">
                        <div class="form-group">
                            <div class="col-xs-12">
                                <h2>
                                    <c:choose>
                                        <c:when test="${ip!=null}">
                                            Location not found!
                                        </c:when>
                                    </c:choose>
                                </h2>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <h2>${location}</h2>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
