<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	exclude-result-prefixes="xs"
	version="2.0">
<!-- =========================================================================================== -->
	<xsl:template match="/">
		<xsl:element name="root">
			<xsl:element name="date">
				<xsl:attribute name="yyyymmdd"><xsl:value-of select="//yyyymmdd"/></xsl:attribute>
			</xsl:element>
		</xsl:element>
    </xsl:template>
<!-- =========================================================================================== -->
</xsl:stylesheet>