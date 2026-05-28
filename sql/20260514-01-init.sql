CREATE DATABASE GestionStock;

CREATE TABLE TypeValorisation (
    id BIGINT PRIMARY KEY,
    designation VARCHAR(30) NOT NULL
);

-- 1. Type de mouvement (Classe TypeMvt)
CREATE SEQUENCE type_mvt_id_seq;
CREATE TABLE TypeMvt (
    id BIGINT PRIMARY KEY DEFAULT nextval('type_mvt_id_seq'),
    designation VARCHAR(30) NOT NULL
);
-- 2. Article (Classe Article)
CREATE SEQUENCE article_id_seq;
CREATE TABLE Article (
    id BIGINT PRIMARY KEY DEFAULT nextval('article_id_seq'),
    libelle VARCHAR(30) NOT NULL
);

ALTER TABLE Article ADD COLUMN IdTypeValorisation BIGINT REFERENCES TypeValorisation(id);

-- 3. État de Stock (Classe EtatStock)
CREATE SEQUENCE etat_stock_id_seq;
CREATE TABLE EtatStock (
    id BIGINT PRIMARY KEY DEFAULT nextval('etat_stock_id_seq'),
    daty DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 4. État de Stock Fille (Classe EtatStockFille)
CREATE SEQUENCE etat_stock_fille_id_seq;
CREATE TABLE EtatStockFille (
    id BIGINT PRIMARY KEY DEFAULT nextval('etat_stock_fille_id_seq'),
    -- Nom exact des attributs Java
    articleId BIGINT REFERENCES Article(id), 
    quantite BIGINT NOT NULL,
    -- Clé étrangère vers la table mère
    id_mere BIGINT REFERENCES EtatStock(id) 
);

-- 5. Mouvement de Stock (Classe MvtStock)
CREATE SEQUENCE mvt_stock_id_seq;
CREATE TABLE MvtStock (
    id BIGINT PRIMARY KEY DEFAULT nextval('mvt_stock_id_seq'),
    daty DATE NOT NULL,
    -- Si votre attribut s'appelle 'TypeMvt' dans la classe MvtStock :
    "TypeMvt" BIGINT REFERENCES TypeMvt(id), 
    observation VARCHAR(30)
);

-- 6. Mouvement de Stock Detail (Classe MvtStockDetail)
CREATE SEQUENCE mvt_stock_detail_id_seq;
CREATE TABLE MvtStockDetail (
    id BIGINT PRIMARY KEY DEFAULT nextval('mvt_stock_detail_id_seq'),
    -- Correspond au champ 'idMvtStockMere'
    idMvtStockMere BIGINT REFERENCES MvtStock(id) ON DELETE CASCADE,
    -- Correspond au champ 'articleId' (si présent dans la classe)
    articleId BIGINT REFERENCES Article(id), 
    quantite BIGINT NOT NULL
);

ALTER TABLE MvtStockDetail ADD COLUMN prixUnitaire BIGINT;