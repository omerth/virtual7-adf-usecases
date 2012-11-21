<?xml version="1.0" encoding="ISO-8859-1" standalone="yes"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
 <xsl:template match="ROOT">
  <fo:root>
   <fo:layout-master-set>
    <fo:simple-page-master master-name="Page" page-height="29.7cm" page-width="21cm" margin-top="0cm"
                           margin-bottom="0cm" margin-left="2cm" margin-right="2cm">
     <fo:region-body margin-top="1.5cm" margin-bottom="1.5cm"/>
    </fo:simple-page-master>
   </fo:layout-master-set>
   <fo:page-sequence master-reference="Page">
    <fo:flow flow-name="xsl-region-body">
     <fo:block>
      <fo:table id="t1" table-layout="fixed" width="18cm" border-style="thick">
       <fo:table-column column-width="18cm"/>
       <fo:table-body>
        <fo:table-row>
         <fo:table-cell border="solid">
          <fo:block font-size="12px">First Name</fo:block>
         </fo:table-cell>
        </fo:table-row>
        <fo:table-row>
         <fo:table-cell border="solid">
          <xsl:apply-templates select="EMPLOYEE_NAME"/>
         </fo:table-cell>
        </fo:table-row>
       </fo:table-body>
      </fo:table>
     </fo:block>
    </fo:flow>
   </fo:page-sequence>
  </fo:root>
 </xsl:template>
 <xsl:template name="EMPLOYEE_NAME" match="EMPLOYEE_NAME">
  <fo:block>
   <xsl:value-of select="EMPLOYEE_FIRST_NAME"/>
  </fo:block>
 </xsl:template>
</xsl:stylesheet>