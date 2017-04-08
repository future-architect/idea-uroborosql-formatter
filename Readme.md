IntelliJ uroboroSQL Formatter
=============================

UroboroSQL Formatter is often used in enterprise systems, For formatting to a highly maintainable style even for very long SQL (1 K step or more) It is a plug-in of IntelliJ IDEA.

In particular, in countries where English is not their mother tongue, such as Japan, comments may be included in SELECT clauses. In that case, we will align the vertical position of the AS clause and the comment, pursuing the viewability which can be said as artistic anymore, This was developed to realize this automatically.

for Japanese, [Readme.ja.md](Readme.ja.md)

#### In case of general formatter

```sql
SELECT MI.MAKER_CD AS ITEM_MAKER_CD -- メーカーコード
,
       MI.BRAND_CD AS ITEM_BRAND_CD -- ブランドコード
,
       MI.ITEM_CD AS ITEM_CD -- 商品コード
,
       MI.CATEGORY AS ITEM_CATEGORY -- 商品カテゴリ
FROM M_ITEM MI -- 商品マスタ

WHERE 1 = 1
  AND MI.ARRIVAL_DATE = '2016-12-01' -- 入荷日
```

#### In case of uroboroSQL Formatter

```sql
SELECT
    MI.MAKER_CD AS  ITEM_MAKER_CD   -- メーカーコード
,   MI.BRAND_CD AS  ITEM_BRAND_CD   -- ブランドコード
,   MI.ITEM_CD  AS  ITEM_CD         -- 商品コード
,   MI.CATEGORY AS  ITEM_CATEGORY   -- 商品カテゴリ
FROM
    M_ITEM  MI  -- 商品マスタ
WHERE
    1               =   1
AND MI.ARRIVAL_DATE =   '2016-12-01'    -- 入荷日

```

Features
--------

-	Formatting by aligning the vertical position of AS and operator in SELECT clause, WHERE clause
-	Even if there is a comment at the end of each line, the format

How to Use
----------

-	Select "Format SQL" from the Code menu
-	Use Alt + Shift + L(Cmd + SHift + L on OS X) to format currently active document.</p>

License
-------

[python-sqlparse library](https://github.com/andialbrecht/sqlparse) and this code are both on [2-clauses BSD](http://www.opensource.org/licenses/bsd-license.php)

---

Copyright 2017 by Future Architect.
