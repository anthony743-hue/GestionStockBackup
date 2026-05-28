INSERT INTO Article (libelle, "IdTypeValorisation") VALUES ('Biscuit2',2);

INSERT INTO MvtStock (daty, "TypeMvt") VALUES ('2026-05-20',1);

INSERT INTO MvtStockDetail (idMvtStockMere, articleid, quantite, prixunitaire)
VALUES (1, 5, 5, 100),
(1,5,10,200);

INSERT INTO MvtStock (daty, "TypeMvt") VALUES ('2026-05-21',2);

INSERT INTO MvtStockDetail (idMvtStockMere, articleid, quantite, prixunitaire)
VALUES (2, 5, 12, 0);


INSERT INTO MvtStock (daty, "TypeMvt") VALUES ('2026-05-22',1);

INSERT INTO MvtStockDetail (idMvtStockMere, articleid, quantite, prixunitaire)
VALUES (3, 5, 3, 110),
(3,5,4,120);

INSERT INTO MvtStock (daty, "TypeMvt") VALUES ('2026-05-23',2);

    INSERT INTO MvtStockDetail (idMvtStockMere, articleid, quantite, prixunitaire)
    VALUES (4, 5, 9, 0);

