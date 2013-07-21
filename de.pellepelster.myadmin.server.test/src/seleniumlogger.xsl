<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:template match="/seleniumlog">
		<html>
			<head>
				<title>SeleniumLog <xsl:value-of select="testName"/></title>
			</head>
			<style type="text/css">
				body {
					background: #FFF;
					color: #000;
					font-family: Arial,Liberation Sans,DejaVu Sans,sans-serif;
				}
			
				th {
					text-align: left;
				}
				
				td {
					vertical-align:text-top;
				}
			
				#logentries tr:hover { background-color:#fffbae!important; } /* hovering */
				#logentries tr:nth-child(odd) td { background-color:#fbfbfb } /*odd*/
				#logentries tr:nth-child(even) td { background-color:#e8ecee } /* even*/
				
				a.morelink {
					text-decoration:none;
					outline: none;
				}
				.morecontent span {
					display: none;
				}
			</style>
			
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" />
			<link href="http://pelle.io/static/lightbox2/css/lightbox.css" rel="stylesheet" />
			<script src="http://pelle.io/static/lightbox2/js/lightbox-2.6.min.js" />
		<body>

			<script>
			$(document).ready(function() {
				var ellipsestext = "...";
				var moretext = "more";
				var lesstext = "less";
				$('.more').each(function() {
					var content = $(this).html();
				
					var showChar = content.split('<br/>')[0].length;
			 
					if(content.length > showChar) {
			 
						var c = content.substr(0, showChar);
						var h = content.substr(showChar, content.length - showChar);
			 
						var html = c + '<span class="moreellipses">' + ellipsestext+ '&#160;</span><span class="morecontent"><span>' + h + '</span>&#160;&#160;<a href="" class="morelink">' + moretext + '</a></span>';
			 
						$(this).html(html);
					}
			 
				});
			 
				$(".morelink").click(function(){
					if($(this).hasClass("less")) {
						$(this).removeClass("less");
						$(this).html(moretext);
					} else {
						$(this).addClass("less");
						$(this).html(lesstext);
					}
					$(this).parent().prev().toggle();
					$(this).prev().toggle();
					return false;
				});
			});
			</script>

			<h2><xsl:value-of select="testName"/></h2>

			<xsl:apply-templates select="seleniumLogEntries"/>
			
		</body>
		</html>
	</xsl:template>

	<xsl:template match="//seleniumLogEntries">
		<table id="logentries">
			<tr>
				<th>#</th>
				<th>Event</th>
				<th>Log Text</th>
				<th>Stack</th>
				<th>Screenshot</th>
			</tr>
			<xsl:apply-templates/>
		</table>
	</xsl:template>

	<xsl:template match="seleniumlogentry">
		<tr>
			<td><xsl:value-of select="count(preceding-sibling::seleniumlogentry) + 1"/></td>
			<td><xsl:value-of select="eventName"/></td>
			<td><xsl:value-of select="logText"/></td>
			<td>
				<div class="more">
				<xsl:call-template name="linebreaks">
					<xsl:with-param name="text" select="stack" />
				</xsl:call-template>
				</div>
			</td>
			<td>
				<a>
					<xsl:attribute name="data-lightbox">image-<xsl:value-of select="screenshot"/></xsl:attribute>
					<xsl:attribute name="href">screenshots/<xsl:value-of select="screenshot"/></xsl:attribute>
					<xsl:attribute name="title"><xsl:value-of select='concat(concat(eventName, ": "), logText)'/></xsl:attribute>
					Screenshot
				</a>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="linebreaks">
		<xsl:param name="text" select="."/>
		<xsl:choose>
			<xsl:when test="contains($text, '&#xa;')">
				<xsl:value-of select="substring-before($text, '&#xa;')"/>
				<br/>
				<xsl:call-template name="linebreaks">
					<xsl:with-param name="text" select="substring-after($text, '&#xa;')" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$text"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>