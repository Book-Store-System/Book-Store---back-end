ALTER TABLE book ADD COLUMN description TEXT NOT NULL;
ALTER TABLE book ADD COLUMN profitMargin NUMERIC NOT NULL;
ALTER TABLE book DROP COLUMN status;