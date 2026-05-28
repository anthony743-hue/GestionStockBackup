CREATE OR REPLACE VIEW MvtStockLib AS
SELECT
    msd.id                 AS idMvtDetail,
    msd.quantite           AS quantite,
    msd.prixUnitaire       AS pu,            -- alias pour coller à l'attribut Java
    tm.designation         AS type,
    tv.designation         AS typeVal,
    ms.daty                AS daty,
    msd.articleId          AS ArticleId
FROM MvtStockDetail msd
JOIN MvtStock ms ON msd.idMvtStockMere = ms.id
JOIN TypeMvt tm ON ms."TypeMvt" = tm.id
JOIN Article a ON msd.articleId = a.id
JOIN TypeValorisation tv ON a.IdTypeValorisation = tv.id;