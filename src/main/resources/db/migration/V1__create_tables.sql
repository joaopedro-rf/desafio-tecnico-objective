CREATE TABLE pagamento_tipos(
    id VARCHAR(3) PRIMARY KEY
);

INSERT INTO pagamento_tipos (id) values('P'),('C'),('D');

CREATE TABLE contas(
    saldo DECIMAL(18, 2) NOT NULL,
    numero_conta BIGINT PRIMARY KEY,
    CONSTRAINT saldo_positivo CHECK(saldo >= 0)
);

CREATE TABLE transacoes(
    id BIGSERIAL PRIMARY KEY,
    contas_numero_conta BIGINT NOT NULL,
    valor DECIMAL(18,2) NOT NULL,
    forma_pagamento VARCHAR(3) NOT NULL,
    FOREIGN KEY (forma_pagamento) REFERENCES pagamento_tipo(id),
    FOREIGN KEY (contas_numero_conta) REFERENCES contas(numero_conta)
);