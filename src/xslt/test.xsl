<?xml version="1.0"?> 
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes"/>
	<xsl:param name="title"/>
	<xsl:param name="host"/>
<!-- =========================================================================================== -->
	<xsl:template match="/">
		<xsl:comment><xsl:text>
</xsl:text>XSLT Version: <xsl:value-of select="system-property('xsl:version')" /> <xsl:text>
</xsl:text>XSLT Vendor: <xsl:value-of select="system-property('xsl:vendor')" /> <xsl:text>
</xsl:text>XSLT Vendor URL: <xsl:value-of select="system-property('xsl:vendor-url')" /> <xsl:text>
</xsl:text></xsl:comment>
		<table border="1" class="InnerTable">
			<tr>
				<th colspan="3" align="center">
					<xsl:value-of select="$title"/>
					<xsl:if test="string-length($host) > 0">
						for client host <xsl:value-of select="$host"/>
					</xsl:if>
				</th>
			</tr>
			<tr>
				<th>feedback</th>
				<th>timestamp</th>
				<th>CDATA</th>
			</tr>
			<xsl:apply-templates/>
		</table>
	</xsl:template>
	<!-- ======================================================================================= -->
	<xsl:template match="example">
		<xsl:element name="tr">
			<td align="left" valign="top">
				<xsl:value-of select="@feedback"/>
			</td>
			<td align="left" valign="top">
				<xsl:value-of select="@timestamp"/>
			</td>
			<td align="left" valign="top">
				<pre>
					<xsl:apply-templates/>
				</pre>
			</td>
		</xsl:element>
	</xsl:template>
<!-- =========================================================================================== -->
</xsl:stylesheet>
