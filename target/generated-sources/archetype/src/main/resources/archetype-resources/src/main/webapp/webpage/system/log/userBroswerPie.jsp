#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${symbol_dollar}{pageContext.request.contextPath}" />
<script type="text/javascript">
	${symbol_dollar}(function() {
		${symbol_dollar}(document).ready(function() {
			var chart;
			${symbol_dollar}.ajax({
				type : "POST",
				url : "logController.do?getBroswerBar&reportType=${symbol_dollar}{reportType}",
				success : function(jsondata) {
					data = eval(jsondata);
					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'containerPie',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '用户浏览器比例分析'
						},
						xAxis : {
							categories : [ 'IE9', 'MSIE 7.0', 'MSIE 8.0', 'MSIE 7.0', 'Firefox', 'Chrome' ]
						},
						tooltip : {
							shadow: false,
							percentageDecimals : 1,
							formatter: function() {
            					return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.percentage, 1) +'%';
         					}

						},
						exporting:{  
			                filename:'pie',  
			                 url:'${symbol_dollar}{ctxPath}/logController.do?export'  
			            },  
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								dataLabels : {
									enabled : true,
									color : '${symbol_pound}000000',
									connectorColor : '${symbol_pound}000000',
									formatter : function() {
										return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 1)+"%";
									}
								}
							}
						},
						series : data
					});
				}
			});
		});
	});
</script>
<div id="containerPie" style="width: 80%; height: 80%"></div>


