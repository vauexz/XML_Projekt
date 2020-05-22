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
            <xsl:element name="h2">
                <xsl:text>Lista czytelników: </xsl:text>
            </xsl:element>
            <xsl:for-each select="czytelnik">
                <xsl:sort select="nazwisko"/>
                    <xsl:element name="div">
                        <xsl:attribute name="class">
                            <xsl:value-of select="imie/@plec"/>
                        </xsl:attribute>
                        <xsl:attribute name="id">
                            <xsl:value-of select="@id"/>
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

                        <xsl:element name="div">
                            <xsl:attribute name="class">
                                <xsl:text>kontakt</xsl:text>
                            </xsl:attribute>
                            <xsl:apply-templates select="kontakt"/>
                        </xsl:element>
                        <xsl:text>&#xa;</xsl:text>
                        <xsl:element name="hr"/>

                        <xsl:element name="div">
                            <xsl:attribute name="class">
                                <xsl:text>suma_wypozyczen</xsl:text>
                            </xsl:attribute>
                            <xsl:text>Wypożyczonych książek: </xsl:text>
                            <xsl:variable name="id">
                                <xsl:value-of select="@id"/>
                            </xsl:variable>
                            <xsl:value-of select="count(/biblioteka/wypozyczenia/wypozyczenie[@czytelnik_id=$id])"/>
                        </xsl:element>
                        <xsl:text>&#xa;</xsl:text>
                    
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
    
    <xsl:template match="kontakt">
        <xsl:text>email: </xsl:text>
        <xsl:value-of select="email"/>
        <xsl:element name="br"/>
        <xsl:text>telefon: </xsl:text>
        <xsl:value-of select="telefon"/>
    </xsl:template>

    <xsl:template match="ksiazki">
        <xsl:element name="div">
            <xsl:attribute name="class">
                <xsl:text>ksiazki</xsl:text>
            </xsl:attribute>
            <xsl:element name="h2">
                <xsl:text>Lista książek: </xsl:text>
            </xsl:element>
        
            <xsl:for-each select="ksiazka">
            <xsl:sort select="tytul"/>
                <xsl:element name="div">
                    <xsl:attribute name="class">
                        <xsl:text>ksiazka</xsl:text>
                    </xsl:attribute>
                    <xsl:attribute name="id">
                        <xsl:value-of select="@id"/>
                    </xsl:attribute>
                    
                    <xsl:element name="span">
                        <xsl:attribute name="class">
                            <xsl:text>tytul</xsl:text>
                        </xsl:attribute>
                        <xsl:text>&apos;&apos;</xsl:text>
                        <xsl:value-of select="tytul"/>
                        <xsl:text>&apos;&apos;(</xsl:text>
                        <xsl:value-of select="rok_wydania"/>
                        <xsl:text>)</xsl:text>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>

                    <xsl:element name="span">
                        <xsl:attribute name="class">
                            <xsl:text>autor</xsl:text>
                        </xsl:attribute>
                        <xsl:for-each select="autor">
                            <xsl:value-of select="."/>
                            <xsl:if test="not(position() = last())">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>

                     <xsl:element name="span">
                        <xsl:attribute name="class">
                            <xsl:text>kategoria</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="kategoria"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>

                    <xsl:element name="span">
                        <xsl:attribute name="class">
                            <xsl:text>wydawnictwo</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="wydawnictwo"/>
                    </xsl:element>
                    <xsl:text>&#xa;</xsl:text>

                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template match="wypozyczenia">
        <xsl:element name="div">
            <xsl:attribute name="class">
                <xsl:text>wypozyczenia</xsl:text>
            </xsl:attribute>
            <xsl:element name="h2">
                <xsl:text>Historia wypozyczeń: </xsl:text>
            </xsl:element>
            <xsl:element name="table">
                <xsl:element name="tr">
                    <xsl:element name="th">Data wypozyczenia</xsl:element>
                    <xsl:element name="th">Zwrot do</xsl:element>
                    <xsl:element name="th">Data zwrotu</xsl:element>
                    <xsl:element name="th">Osoba(id)</xsl:element>
                    <xsl:element name="th">Książka(id)</xsl:element>
                </xsl:element>
                <xsl:for-each select="wypozyczenie">
                <xsl:sort select="data_wypozyczenia"/>
                    <xsl:variable name="czytelnik_id" select="@czytelnik_id"/>
                    <xsl:variable name="ksiazka_id" select="@ksiazka_id"/>

                    <xsl:element name="tr">
                        <xsl:element name="tr">
                            <xsl:attribute name="class">
                                <xsl:choose>
                                    <xsl:when test="(data_zwrotu)">oddana</xsl:when>
                                    <xsl:otherwise>nieoddana</xsl:otherwise>
                                </xsl:choose>
                            </xsl:attribute>
                            <xsl:element name="td">
                                <xsl:value-of select="data_wypozyczenia"/>
                            </xsl:element>

                            <xsl:element name="td">
                                <xsl:value-of select="data_do"/>
                            </xsl:element>

                            <xsl:element name="td">
                                <xsl:if test="(data_zwrotu)">
                                    <xsl:value-of select="data_zwrotu"/>
                                </xsl:if>
                            </xsl:element>

                            <xsl:element name="td">
                                <xsl:value-of select="/biblioteka/czytelnicy/czytelnik[@id=$czytelnik_id]/imie"/>
                                <xsl:text>&#160;</xsl:text>
                                <xsl:value-of select="/biblioteka/czytelnicy/czytelnik[@id=$czytelnik_id]/nazwisko"/>
                                <xsl:element name="b">
                                    <xsl:text> (</xsl:text>
                                    <xsl:element name="a">
                                        <xsl:attribute name="href">
                                            <xsl:text>#</xsl:text>
                                            <xsl:value-of select="$czytelnik_id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="$czytelnik_id"/>
                                    </xsl:element>
                                    <xsl:text>) </xsl:text>
                                </xsl:element>
                            </xsl:element>

                            <xsl:element name="td">
                                <xsl:value-of select="/biblioteka/ksiazki/ksiazka[@id=$ksiazka_id]/tytul"/>
                                <xsl:element name="b">
                                    <xsl:text> (</xsl:text>
                                    <xsl:element name="a">
                                        <xsl:attribute name="href">
                                           <xsl:text>#</xsl:text>
                                            <xsl:value-of select="$ksiazka_id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="$ksiazka_id"/>
                                    </xsl:element>
                                    <xsl:text>) </xsl:text>
                                </xsl:element>
                            </xsl:element>

                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>