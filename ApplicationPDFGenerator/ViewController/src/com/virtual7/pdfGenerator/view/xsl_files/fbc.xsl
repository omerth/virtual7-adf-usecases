<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <!-- ========================= -->
  <!-- root element: fbc -->
  <!-- ========================= -->
  <xsl:template match="fbc">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="1cm"
                               margin-left="2cm" margin-right="1cm">
          <fo:region-body region-name="xsl-region-body" margin-top="1cm" margin-bottom="2.5cm"/>
          <fo:region-before region-name="xsl-region-before" extent="1cm"/>
          <fo:region-after region-name="xsl-region-after" extent="2cm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence id="content" master-reference="simpleA4">
        <fo:static-content flow-name="xsl-region-before">
          <fo:block text-align="center" color="#808097" font-size="10px">NATO UNCLASSIFIED</fo:block>
        </fo:static-content>
        <fo:static-content flow-name="xsl-region-after" color="#808097" font-size="10px">
          <fo:block text-align="center" color="#808097" font-size="10px">NATO UNCLASSIFIED</fo:block>
          <fo:block text-align="center">
            <fo:page-number/>
          </fo:block>
        </fo:static-content>
        <fo:flow flow-name="xsl-region-body">
          <fo:block>
            <fo:table id="t1" table-layout="fixed" width="18" border-style="none">
              <fo:table-column column-width="9cm"/>
              <fo:table-column column-width="9cm"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block font-size="12px" font-weight="bold">CO:</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block font-size="12px" margin-left="3cm">EF C POC:</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block>
            <xsl:call-template name="Newline"/>
          </fo:block>
          <fo:block>
            <fo:table id="t20" table-layout="fixed" width="18cm" border-collapse="collapse" border-style="solid"
                      border-width="thick">
              <fo:table-column column-width="2.3cm"/>
              <fo:table-column column-width="13.4cm"/>
              <fo:table-column column-width="2.3cm"/>
              <fo:table-body>
                <fo:table-row id="tr1" height="2.2cm">
                  <fo:table-cell id="titleCell1" border-style="solid" border-right="none" border-width="thick" background-color="#CCCCCC">
                    <fo:block margin-left="2mm" margin-top="1mm">
                      <fo:external-graphic content-width="2cm" content-height="2cm"
                                           src="C:\Users\bolat\Desktop\ApplicationPDFGeneratorTemp\Model\src\nato-logo.png"/>
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="titleCell12" border-style="solid" border-width="thick" background-color="#CCCCCC" border-right="none" border-left="none">
                    <fo:block font-size="24px" font-weight="bold" text-align="center" margin-top="6mm">Final BusinessCase</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="titleCell3" border-style="solid" border-width="thick" border-left="none" background-color="#CCCCCC"> 
                    <fo:block margin-top="1mm">
                      <fo:external-graphic content-width="2cm" content-height="2cm"
                                           src="C:\Users\bolat\Desktop\ApplicationPDFGeneratorTemp\Model\src\nato-logo.png"/>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t2" table-layout="fixed" width="18cm" border-collapse="collapse" border-style="solid"
                      border-width="thick">
              <fo:table-column column-width="4.5cm"/>
              <fo:table-column column-width="4.5cm"/>
              <fo:table-column column-width="4.5cm"/>
              <fo:table-column column-width="4.5cm"/>
              <fo:table-body id="tb1">
                <fo:table-row id="tr2" height="7mm">
                  <fo:table-cell id="headingCell1" border="solid" border-width="thick" number-columns-spanned="3"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" font-weight="bold" text-align="left" margin="1mm">Administrational Data</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell1" border="solid" border-width="thick">
                    <fo:block font-size="11px" text-align="left" margin="1mm">Overall Priority:</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr3" height="5mm">
                  <fo:table-cell id="normalCell2" border="solid" background-color="#F2F2F2" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Nation / Originator</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell3" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"></fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell4" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Core / Export</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell5" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"></fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr4" height="5mm">
                  <fo:table-cell id="normalCell6" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Type (Capab / Support)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell7" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell8" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Applicability</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell9" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr5" height="5mm">
                  <fo:table-cell id="normalCell10" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Date</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell11" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell12" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Required PoE</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCel13" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr6" height="5mm">
                  <fo:table-cell id="normalCell14" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">ID</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell15" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell16" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Mod. Category</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell17" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr7" height="7mm" border-width="thick">
                  <fo:table-cell id="headingCell2" border="solid" border-width="thick" number-columns-spanned="4"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" font-weight="bold" text-align="center" margin="1mm">Description</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr8" height="5mm">
                  <fo:table-cell id="normalCell18" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Short (purpose)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell19" border="solid" number-columns-spanned="3">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr9" height="20mm">
                  <fo:table-cell id="normalCell20" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Summary/detailed description</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell21" border="solid" number-columns-spanned="3">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr10" height="7mm">
                  <fo:table-cell id="headingCell3" border="solid" border-width="thick" number-columns-spanned="4"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" font-weight="bold" text-align="center" margin="1mm">Benefit (time, cost,
                                                                                                   performance) /
                                                                                                   Quantifiable targets</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t3" table-layout="fixed" width="18cm" border-collapse="collapse" border-style="solid"
                      border-width="thick">
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="6.5cm"/>
              <fo:table-column column-width="6.5cm"/>
              <fo:table-body>
                <fo:table-row id="tr11" height="5mm">
                  <fo:table-cell id="normalCell22" border="solid" background-color="black">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell23" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="11px" text-align="left" margin="1mm">Current</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell24" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="11px" text-align="left" margin="1mm">Post - Mod</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr12" height="5mm">
                  <fo:table-cell id="normalCell25" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Time</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell26" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell27" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr13" height="5mm">
                  <fo:table-cell id="normalCell28" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Cost</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell29" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell30" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr14" height="5mm">
                  <fo:table-cell id="normalCell31" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Performance</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell32" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell33" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t4" table-layout="fixed" width="18cm" border-collapse="collapse" border="solid"
                      border-width="thick">
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="2cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="1.4cm"/>
              <fo:table-column column-width="1.4cm"/>
              <fo:table-column column-width="1.4cm"/>
              <fo:table-column column-width="1.4cm"/>
              <fo:table-column column-width="1.4cm"/>
              <fo:table-body>
                <fo:table-row id="tr15" height="7mm">
                  <fo:table-cell id="headingCell4" border="solid" border-width="thick" number-columns-spanned="2"
                                 background-color="#D9D9D9">
                    <fo:block font-size="12px" text-align="center" font-weight="bold">Technical Complexity</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="headingCell5" border="solid" border-width="thick" number-columns-spanned="2"
                                 background-color="#D9D9D9">
                    <fo:block font-size="12px" text-align="center" font-weight="bold">Impact Analysis</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="headingCell6" border="solid" border-width="thick" number-columns-spanned="5"
                                 background-color="#D9D9D9">
                    <fo:block font-size="12px" text-align="center" font-weight="bold">Embodiment Requirements</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr115" height="10mm">
                  <fo:table-cell id="normalCell34" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Requirement complexity</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell35" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell36" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm">PSC required (Y/N)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell37" border="solid" border-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell38" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">In-Line</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell39" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">1st Line</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell40" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm">2nd Line</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell41" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="8px" text-align="center">Existing Retrofit Campaign</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell42" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="8px" text-align="center">New Retrofit Campaign</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr16" height="10mm">
                  <fo:table-cell id="normalCell43" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Design complexity</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell44" border="solid" border-right-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell45" border="solid" number-columns-spanned="2" number-rows-spanned="2"
                                 border-width="thick">
                    <fo:block font-size="9px" text-align="left" margin="1mm">Inpact on FMC tasks (Y/N) / details</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell46" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell47" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell48" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell49" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell50" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row id="tr17" height="10mm">
                  <fo:table-cell id="normalCell51" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="left" margin="1mm">Testing complexity</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell52" border="solid">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell53" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell54" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell55" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell56" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell57" border="solid">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="headingCell7" border="solid" border-width="thick" number-columns-spanned="9"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" text-align="left" font-weight="bold" margin="1mm">A/C Systems and/or
                                                                                                 Support Items to change</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t5" table-layout="fixed" width="18cm" border-collapse="collapse">
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-body>
                <fo:table-row height="10mm">
                  <fo:table-cell id="normalCell58" border="solid" background-color="black">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell59" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="3mm">Negligible</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell60" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="3mm">Low</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell61" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="3mm">Medium</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell62" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="3mm">High</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell63" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="3mm">Very High</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <xsl:apply-templates select="items_to_change_tr"/>
              </fo:table-body>
            </fo:table>
            <fo:table id="t6" table-layout="fixed" width="18cm" border-collapse="collapse" border="solid"
                      border-width="thick">
              <fo:table-column column-width="2.75cm"/>
              <fo:table-column column-width="1.5cm"/>
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="2.75cm"/>
              <fo:table-column column-width="1cm"/>
              <fo:table-column column-width="1cm"/>
              <fo:table-column column-width="1cm"/>
              <fo:table-body>
                <fo:table-row height="10mm">
                  <fo:table-cell id="headingCell9" border="solid" border-width="thick" number-columns-spanned="3"
                                 background-color="#D9D9D9">
                    <fo:block font-size="12px" text-align="left" font-weight="bold" margin="1mm">Qualification/Certification
                                                                                                 Requirements</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="headingCell10" border="solid" border-width="thick" number-columns-spanned="5"
                                 background-color="#D9D9D9">
                    <fo:block font-size="12px" text-align="left" font-weight="bold" margin="1mm" margin-top="3mm">Risk
                                                                                                                  Analysis</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell64" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">Level</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell65" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin-left="1mm" margin-top="2mm">Required (Y/N)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell66" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">Rig Requirement</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell67" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">Description</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell68" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">Probability</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell69" border="solid" border-width="thick" number-columns-spanned="3"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm">Impact</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell70" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm">Time</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell71" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm">Cost</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell72" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" margin="1mm">Pref.</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="10mm">
                  <fo:table-cell id="normalCell73" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">LRI</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell74" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell75" border="solid" border-right-width="thick">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell76" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell77" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell78" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell79" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell80" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="10mm">
                  <fo:table-cell id="normalCell81" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm" margin-top="3mm">Subsystem</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell82" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell83" border="solid" border-right-width="thick">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell84" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell85" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell86" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell87" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell88" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="10mm">
                  <fo:table-cell id="normalCell89" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm">System Integration</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell90" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell91" border="solid" border-right-width="thick">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell92" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell93" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell94" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell95" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell96" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="10mm">
                  <fo:table-cell id="normalCell97" border="solid" border-right-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="center" margin="1mm">A/C Integration &amp; FT</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell98" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell99" border="solid" border-right-width="thick">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell100" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell101" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell102" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell103" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell104" border="solid">
                    <fo:block font-size="9px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t7" table-layout="fixed" width="18cm" border-collapse="collapse" border="solid"
                      border-width="thick">
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-body>
                <fo:table-row height="7mm">
                  <fo:table-cell id="headingCell11" border="solid" border-width="thick" number-columns-spanned="6"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" text-align="center" font-weight="bold" margin-top="1mm">Resources</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="normalCell105" border="solid" border-width="thick" number-rows-spanned="2"
                                 background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="5mm">Phase</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell106" border="solid" border-width="thick" number-columns-spanned="5"
                                 background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">Time</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="normalCell107" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">Negligible</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell108" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">Low</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell109" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">Medium</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell110" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">High</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell111" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="10px" text-align="center" margin-top="2mm">Very High</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell112" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">Study</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell113" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell114" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell115" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell116" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell117" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell118" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">Definition</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell119" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell120" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell121" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell122" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell123" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell124" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">Design</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell125" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell126" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell127" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell128" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell129" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell130" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">Qual/Cert</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell131" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell132" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell133" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell134" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell135" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell136" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">Embodiment</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell137" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell138" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell139" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell140" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell141" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="5mm">
                  <fo:table-cell id="normalCell142" border="solid" border-right-width="thick"
                                 background-color="#D9D9D9">
                    <fo:block font-size="10px" text-align="center" margin-top="1mm">ILS</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell143" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell144" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell145" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell146" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell147" border="solid">
                    <fo:block font-size="10px" text-align="center"/>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t8" table-layout="fixed" width="18cm" border-collapse="collapse" border="solid"
                      border-width="thick">
              <fo:table-column column-width="5.5cm"/>
              <fo:table-column column-width="5.5cm"/>
              <fo:table-column column-width="3.5cm"/>
              <fo:table-column column-width="3.5cm"/>
              <fo:table-body>
                <fo:table-row height="7mm">
                  <fo:table-cell id="headingCell12" border="solid" border-width="thick" number-columns-spanned="4"
                                 background-color="#D9D9D9">
                    <fo:block font-size="14px" text-align="center" font-weight="bold" margin="1mm" margin-top="2mm">Estimate
                                                                                                                    Cost</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="10mm">
                  <fo:table-cell id="headingCell13" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="11px" text-align="left" font-weight="bold" margin="1mm">Engineering Judgement
                                                                                                 Bugetary Estimate Cost</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell148" border="solid" border-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="headingCell15" border="solid" border-width="thick" background-color="#F2F2F2">
                    <fo:block font-size="11px" text-align="left" font-weight="bold" margin="1mm" margin-top="3mm">Cost
                                                                                                                  Score</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell149" border="solid" border-width="thick">
                    <fo:block font-size="10px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="15mm">
                  <fo:table-cell id="normalCell1999" border="solid" border-width="thick" number-columns-spanned="4">
                    <fo:block font-size="10px" text-align="left" margin-left="1mm">Exclusions:</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t9" table-layout="fixed" width="18cm" border="solid" border-width="thick"
                      border-collapse="collapse">
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-body>
                <fo:table-row height="7mm">
                  <fo:table-cell id="headingCell14" border="solid" border-width="thick" number-columns-spanned="6"
                                 background-color="#F2F2F2">
                    <fo:block font-size="14px" text-align="center" font-weight="bold" margin-top="1mm">E2E Phases
                                                                                                       Estimate Cost
                                                                                                       Breakdown</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="normalCell150" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">Study</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell151" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">Definition</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell152" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">Design</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell153" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">Qual /
                                                                                                                 Certif</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell154" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">Embodiment</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell155" border="solid" border-bottom-width="thick"
                                 background-color="#F2F2F2">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="2mm">ILS</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="normalCell156" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left">
                      <xsl:call-template name="Newline"/>
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell157" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell158" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell159" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell160" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell161" border="solid" border-bottom-width="thick">
                    <fo:block font-size="9px" text-align="left"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="15mm">
                  <fo:table-cell id="normalCell162" border="solid" border-width="thick" number-columns-spanned="6">
                    <fo:block font-size="9px" text-align="left" margin-left="1mm">Comments:</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <fo:table id="t10" table-layout="fixed" width="18cm" border-collapse="collapse">
              <fo:table-column column-width="3.5cm"/>
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5.5cm"/>
              <fo:table-body>
                <fo:table-row height="15mm">
                  <fo:table-cell id="normalCell163" border="solid" border-width="thick">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="5mm">Approval
                                                                                                                 date</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell164" border="solid" border-width="thick">
                    <fo:block font-size="9px" text-align="left" font-weight="bold"/>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell165" border="solid" border-width="thick">
                    <fo:block font-size="9px" text-align="left" font-weight="bold" margin="1mm" margin-top="5mm">Approval
                                                                                                                 Name
                                                                                                                 and
                                                                                                                 Position</fo:block>
                  </fo:table-cell>
                  <fo:table-cell id="normalCell166" border="solid" border-width="thick">
                    <fo:block font-size="9px" text-align="left" font-weight="bold"/>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="7mm">
                  <fo:table-cell id="normalCell167" border="solid" number-columns-spanned="4" border-width="thick">
                    <fo:block font-size="9px" text-align="left" margin="1mm" margin-top="2mm">See assessment criteria
                                                                                              and score rules on the
                                                                                              following page</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <xsl:call-template name="Newline"/>
          <xsl:call-template name="Newline"/>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px" text-align="left" font-weight="bold" text-decoration="underline">Assesment Criteria
                                                                                                      for completing an
                                                                                                      FRCP Final
                                                                                                      Business Case</fo:block>
          <xsl:call-template name="Newline"/>
          <fo:table id="t11" table-layout="fixed" width="14cm" border-collapse="collapse">
            <fo:table-column column-width="2.5cm"/>
            <fo:table-column column-width="11.5cm"/>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell id="normalCell168" border="none" number-columns-spanned="2">
                  <fo:block font-size="10px" text-align="left" font-weight="bold">System(s)/Facilities requiring to be
                                                                                  changed (Level of change required)</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell169" border="solid" number-columns-spanned="2">
                  <fo:block font-size="10px" text-align="center">Key</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell170" border="solid">
                  <fo:block font-size="10px" text-align="center">Negligible</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell171" border="solid">
                  <fo:block font-size="10px" text-align="left" space-before="2mm">Minor amendments requiring no part
                                                                                  number changes</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell172" border="solid">
                  <fo:block font-size="10px" text-align="center">Low</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell173" border="solid">
                  <fo:block font-size="10px" text-align="left" space-before="2mm">Changes limited to sub-system level</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell174" border="solid">
                  <fo:block font-size="10px" text-align="center">Medium</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell175" border="solid">
                  <fo:block font-size="10px" text-align="left" space-before="2mm">Changes limited to system level</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell176" border="solid">
                  <fo:block font-size="10px" text-align="center">High</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell177" border="solid">
                  <fo:block font-size="10px" text-align="left" space-before="2mm">Changes affect more than one system</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell178" border="solid">
                  <fo:block font-size="10px" text-align="center">Very High</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell179" border="solid">
                  <fo:block font-size="10px" text-align="left" space-before="2mm">Changes affect a large number (&lt;3)
                                                                                  of inter-related systems</fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px" text-align="left" font-weight="bold">Resources</fo:block>
          <fo:table id="t12" table-layout="fixed" width="8cm" border-collapse="collapse">
            <fo:table-column column-width="2cm"/>
            <fo:table-column column-width="3cm"/>
            <fo:table-column column-width="3cm"/>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell id="normalCell180" border="solid" number-columns-spanned="3">
                  <fo:block font-size="10px" text-align="center">Key</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell181" border="solid">
                  <fo:block font-size="10px" text-align="center"/>
                </fo:table-cell>
                <fo:table-cell id="normalCell182" border="solid">
                  <fo:block font-size="10px" text-align="center">
                    <fo:inline font-weight="bold">Time</fo:inline>
                    (months)
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell183" border="solid">
                  <fo:block font-size="10px" text-align="center">
                    <fo:inline font-weight="bold">Cost</fo:inline>
                    (€)
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell184" border="solid">
                  <fo:block font-size="10px" text-align="center">Negligible</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell185" border="solid">
                  <fo:block font-size="10px" text-align="center">&lt;1</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell186" border="solid">
                  <fo:block font-size="10px" text-align="center">&lt;10k</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell187" border="solid">
                  <fo:block font-size="10px" text-align="center">Low</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell188" border="solid">
                  <fo:block font-size="10px" text-align="center">1-3</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell189" border="solid">
                  <fo:block font-size="10px" text-align="center">10-100k</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell190" border="solid">
                  <fo:block font-size="10px" text-align="center">Medium</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell191" border="solid">
                  <fo:block font-size="10px" text-align="center">3-6</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell192" border="solid">
                  <fo:block font-size="10px" text-align="center">100k-1m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell193" border="solid">
                  <fo:block font-size="10px" text-align="center">High</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell194" border="solid">
                  <fo:block font-size="10px" text-align="center">6-12</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell195" border="solid">
                  <fo:block font-size="10px" text-align="center">1-10m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell210" border="solid">
                  <fo:block font-size="10px" text-align="center">Very High</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell211" border="solid">
                  <fo:block font-size="10px" text-align="center">&gt;12</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell212" border="solid">
                  <fo:block font-size="10px" text-align="center">&gt;10m</fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px" text-align="left" font-weight="bold">Overall Cost Score (€)</fo:block>
          <fo:table id="t13" table-layout="fixed" width="8cm" border-collapse="collapse">
            <fo:table-column column-width="1.5cm"/>
            <fo:table-column column-width="3cm"/>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell id="normalCell213" border="solid">
                  <fo:block font-size="10px" text-align="center">Key</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell214" border="solid">
                  <fo:block font-size="10px" text-align="center"/>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell215" border="solid">
                  <fo:block font-size="10px" text-align="center">1</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell216" border="solid">
                  <fo:block font-size="10px" text-align="center">&lt;500k</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell208" border="solid">
                  <fo:block font-size="10px" text-align="center">2</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell209" border="solid">
                  <fo:block font-size="10px" text-align="center">501-1m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell197" border="solid">
                  <fo:block font-size="10px" text-align="center">3</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell198" border="solid">
                  <fo:block font-size="10px" text-align="center">1m-5m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell199" border="solid">
                  <fo:block font-size="10px" text-align="center">4</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell200" border="solid">
                  <fo:block font-size="10px" text-align="center">5-20m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell201" border="solid">
                  <fo:block font-size="10px" text-align="center">5</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell217" border="solid">
                  <fo:block font-size="10px" text-align="center">5-20m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell202" border="solid">
                  <fo:block font-size="10px" text-align="center">6</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell203" border="solid">
                  <fo:block font-size="10px" text-align="center">50-100m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell204" border="solid">
                  <fo:block font-size="10px" text-align="center">7</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell205" border="solid">
                  <fo:block font-size="10px" text-align="center">100-500m</fo:block>
                </fo:table-cell>
              </fo:table-row>
              <fo:table-row>
                <fo:table-cell id="normalCell206" border="solid">
                  <fo:block font-size="10px" text-align="center">8</fo:block>
                </fo:table-cell>
                <fo:table-cell id="normalCell207" border="solid">
                  <fo:block font-size="10px" text-align="center">500m +</fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px">Note: The cost score will be made by NN based on the budgetary estimate provided by
                                     Industry</fo:block>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px">
            <fo:inline font-weight="bold">Engineering Judgement Budgetary Estimate Cost:</fo:inline>
            it´s an indicative preliminary estimate cost based on the best engineering judgement available, it´s free of
            any allowance to cover risks, it´s not binding and serve no useful contractual purpose.
          </fo:block>
          <xsl:call-template name="Newline"/>
          <fo:block font-size="10px">When the estimate is quoted subject to exclusions, those exclusions must be
                                     detailed.</fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <!-- ==================================== -->
  <!-- child element: items_to_change_table -->
  <!-- ==================================== -->
  <xsl:template match="items_to_change_tr">
    <fo:table-row>
      <fo:table-cell border="solid">
        <fo:block font-size="9px" text-align="left">
          <xsl:value-of select="item"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell border="solid">
        <fo:block font-size="10px" text-align="left"/>
      </fo:table-cell>
      <fo:table-cell border="solid">
        <fo:block font-size="10px" text-align="left"/>
      </fo:table-cell>
      <fo:table-cell border="solid">
        <fo:block font-size="10px" text-align="left"/>
      </fo:table-cell>
      <fo:table-cell border="solid">
        <fo:block font-size="10px" text-align="left"/>
      </fo:table-cell>
      <fo:table-cell border="solid">
        <fo:block font-size="10px" text-align="left"/>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
  <xsl:template name="Newline">
    <fo:block>
      <fo:leader/>
    </fo:block>
  </xsl:template>
</xsl:stylesheet>
