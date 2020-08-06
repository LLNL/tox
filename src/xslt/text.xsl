<?xml version="1.0"?> 
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" media-type="text/plain"/>
<!-- =========================================================================================== -->
	<xsl:template match="/">
		<xsl:text>
</xsl:text>XSLT Version: <xsl:value-of select="system-property('xsl:version')" /> <xsl:text>
</xsl:text>XSLT Vendor: <xsl:value-of select="system-property('xsl:vendor')" /> <xsl:text>
</xsl:text>XSLT Vendor URL: <xsl:value-of select="system-property('xsl:vendor-url')" /> <xsl:text>
</xsl:text><xsl:text>
</xsl:text>
		<xsl:apply-templates select="toText"/>
	</xsl:template>
	<!-- ======================================================================================= -->
	<xsl:template match="toText">
		<xsl:text>toText >> </xsl:text><xsl:value-of select="@feedback"/><xsl:text> </xsl:text><xsl:value-of select="@timestamp"/><xsl:text>
</xsl:text>
		<xsl:apply-templates select="testing"/>
	</xsl:template>
	<!-- ======================================================================================= -->
	<xsl:template match="testing">
		<xsl:text>testing >> </xsl:text><xsl:value-of select="@key"/><xsl:text> </xsl:text><xsl:value-of select="@txt"/><xsl:text> </xsl:text><xsl:value-of select="@seq"/><xsl:text>
</xsl:text>
	</xsl:template>
<!-- =========================================================================================== -->
</xsl:stylesheet>
