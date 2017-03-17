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
<body>
<title>Check IP</title>
<script src="https://api-maps.yandex.ru/2.1/?lang=en_US" type="text/javascript"></script>
<script type="text/javascript">    ymaps.ready(function () {
        var myMap = new ymaps.Map('map_clusters', {
            center: [0, 0],
            zoom: 1,
            controls: ['zoomControl', 'typeSelector'],
            behaviors: ['default', 'scrollZoom']
        });
        clusterer = new ymaps.Clusterer({
            preset: 'islands#invertedVioletClusterIcons',
            groupByCoordinates: false,
            clusterDisableClickZoom: true,
            clusterHideIconOnBalloonOpen: false,
            geoObjectHideIconOnBalloonOpen: false
        }),
            getPointData = function (position) {
                return {
                    balloonContentHeader: 'IP ' + (position.ip + 1),
                    balloonContentBody: getContentBody(position)

                }

            },
            getPointOptions = function () {
                return {
                    preset: 'islands#violetIcon'
                };
            },
            points = ${points},
            geoObjects = [];

        function getContentBody(position) {
            var str='';
            for (key in position) {
                if (position.hasOwnProperty(key)) {
                    var value = position[key];
                    str = str + key + ':' + value + '</br>'
                }
            }
            return str;
        }

        var jpositions =${jpositions};

        for (var i = 0, len = points.length; i < len; i++) {
            geoObjects[i] = new ymaps.Placemark(points[i],
                getPointData(jpositions[i]),
                getPointOptions());
        }
        clusterer.options.set({
            gridSize: 50,
            clusterDisableClickZoom: true
        });

        clusterer.add(geoObjects);
        myMap.geoObjects.add(clusterer);


        myMap.setBounds(clusterer.getBounds(), {
            checkZoomRange: true
        });
    }
)
;
</script>

<div class="container" style="alignment: center">
    <form:form name="form" action="" method="post">
        <br>
        <div>
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
            <div class="row">
                <div class="form-group">
                    <div class="col-xs-12">
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
            <c:choose>
                <c:when test="${positions!=null && positions.size()!=0}">
                    <div id="map_clusters" style="width: 100%; height: 100%;"></div>
                </c:when>
            </c:choose>
            <br>
            <div class="row" style="text-align: center">
                <c:choose>
                    <c:when test="${ip!=null}">
                        <div class="form-group">
                            <div class="col-xs-12" style="font-size: small">
                                    ${ip}
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            <br>
            <div class="row" style="text-align: center">
                <c:choose>
                    <c:when test="${positions!=null && positions.size()!=0}">
                        <div class="form-group">
                            <div class="col-xs-12" style="font-size: small">
                                <table class="table" border="1">
                                    <tr>
                                        <td><b>IP</b></td>
                                        <td><b>Country code</b></td>
                                        <td><b>Country name</b></td>
                                        <td><b>Region</b></td>
                                        <td><b>Region name</b></td>
                                        <td><b>City</b></td>
                                        <td><b>Postal code</b></td>
                                        <td><b>Latitude</b></td>
                                        <td><b>Longitude</b></td>
                                    <tr/>


                                    <c:forEach items="${positions}" var="position">
                                        <tr>
                                            <td>${position.ip}</
                                            ></td>
                                            <td>${position.countryCode}</td>
                                            <td>${position.countryName}</td>
                                            <td>${position.region}</td>
                                            <td>${position.regionName}</td>
                                            <td>${position.city}</td>
                                            <td>${position.postalCode}</td>
                                            <td>${position.latitude}</td>
                                            <td>${position.longitude}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group">
                            <div class="col-xs-12" style="font-size: small">
                                <c:choose>
                                    <c:when test="${ip!=null}">
                                        Location not found!
                                    </c:when>
                                </c:choose>
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
