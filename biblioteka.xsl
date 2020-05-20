<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="biblioteka.css"/>
                <title>Biblioteka</title>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="czytelnicy">
        <xsl:element name="div">
        <xsl:attribute name="class">
            <xsl:text>czytelnicy</xsl:text>
        </xsl:attribute>
            <xsl:for-each select="czytelnik">
                <xsl:element name="div">
                    <xsl:attribute name="class">
                        <xsl:value-of select="imie/@plec"/>
                    </xsl:attribute>

                    <xsl:element name="div">
                        <xsl:attribute name="class">
                            <xsl:text>ID</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="@id"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>
                    <xsl:element name="hr"/>

                    <xsl:element name="div">
                        <xsl:attribute name="class">
                            <xsl:text>podpis</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="imie"/>
                        <xsl:text>&#160;</xsl:text>
                        <xsl:value-of select="nazwisko"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>
                    <xsl:element name="hr"/>

                    <xsl:element name="div">
                        <xsl:attribute name="class">
                            <xsl:text>data_urodzenia</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="data_urodzenia"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>
                    <xsl:element name="hr"/>

                    <xsl:element name="div">
                        <xsl:attribute name="class">
                            <xsl:text>adres</xsl:text>
                        </xsl:attribute>
                        <xsl:apply-templates select="adres"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>
                    <xsl:element name="hr"/>
                
                </xsl:element>
                <xsl:text>&#xa;</xsl:text>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template match="adres">
        <xsl:value-of select="miasto/@kod_pocztowy"/>
        <xsl:text>&#160;</xsl:text>
        <xsl:value-of select="miasto"/>
        <xsl:element name="br"/>
        <xsl:text>ul.&#160;</xsl:text>
        <xsl:value-of select="ulica"/>
        <xsl:text>&#160;</xsl:text>
        <xsl:value-of select="nr_domu"/>
        <xsl:if test="(nr_mieszkania)">
            <xsl:text>&#160;/&#160;</xsl:text>
            <xsl:value-of select="nr_mieszkania"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="ksiazki">
    </xsl:template>

    <xsl:template match="wypozyczenia">
    </xsl:template>

</xsl:stylesheet>