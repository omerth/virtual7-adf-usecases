<?xml version="1.0" encoding="ISO-8859-1" standalone="yes"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]> 
<xsl:stylesheet  version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

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
                                            <fo:block>
						<xsl:apply-templates select="CONTENT_TABLE" />
                                            </fo:block>
					</fo:flow>
				</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template match="EMPLOYEE_FIRST_NAME" >
			<fo:table-cell display-align="center" padding-left="5pt"
							border-left-style="solid" border-left-width="thin" border-left-color="#000000"
							border-top-style="solid" border-top-width="thin" border-top-color="#000000"
							border-right-style="solid" border-right-width="thin" border-right-color="#000000"
							border-bottom-style="solid" border-bottom-width="thin" border-bottom-color="#000000">
				<fo:block font-size="12pt" font-family="verdana" color="blue" text-align="left">
					<xsl:apply-templates />
				</fo:block>
			</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="EMPLOYEE_LAST_NAME" >
		<fo:table-cell display-align="center" padding-left="5pt"
					border-left-style="solid" border-left-width="thin" border-left-color="#000000"
					border-top-style="solid" border-top-width="thin" border-top-color="#000000"
					border-right-style="solid" border-right-width="thin" border-right-color="#000000"
					border-bottom-style="solid" border-bottom-width="thin" border-bottom-color="#000000">
			<fo:block font-size="12pt" font-family="verdana" color="red" text-align="left">
				<xsl:apply-templates />
			</fo:block>
		</fo:table-cell>
	</xsl:template>
	
	<xsl:template match="EMPLOYEE_NAME">
		<fo:table-row>
			<xsl:apply-templates  select="EMPLOYEE_FIRST_NAME"/>
			<xsl:apply-templates  select="EMPLOYEE_LAST_NAME"/>
		</fo:table-row>
	</xsl:template>
	
	<xsl:template match="CONTENT_TABLE">
		<fo:table >
			<fo:table-column column-width="9cm"/>
			<fo:table-column column-width="9cm"/>
			<fo:table-body>
				<xsl:apply-templates  select="EMPLOYEE_NAME"/>
                                <xsl:apply-templates  select="EMPLOYEE_FIRST_NAME"/>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet>