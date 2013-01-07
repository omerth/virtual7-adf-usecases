<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="ROOT">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="Page"
						page-height="29.7cm"
						page-width="21cm"
						margin-top="0cm"
						margin-bottom="0cm"
						margin-left="2cm"
						margin-right="2cm">
					<fo:region-body margin-top="1.5cm" margin-bottom="1.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
				<fo:page-sequence master-reference="Page">
					<fo:flow flow-name="xsl-region-body">
						<xsl:apply-templates select="GRAPH_IMG" />
					</fo:flow>
				</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template match="GRAPH_IMG" >
		<fo:block font-size="12pt" text-align="right">
			<fo:external-graphic height="90pt" width="130pt" src="{/ROOT/GRAPH_IMG}"/>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>