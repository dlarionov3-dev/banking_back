-- Отключаем индексы на время вставки для ускорения (опционально, если база пустая)
-- SET session_replication_role = 'replica';

BEGIN;

-- 1. Генерация данных для таблицы USERS
-- Используем CTE, чтобы потом использовать эти же ID для профилей и аккаунтов
DO $$
DECLARE
    row_count INTEGER := 500000;
BEGIN
    INSERT INTO users (id, email)
--    INSERT INTO users (id, email, created_at, updated_at)
    SELECT
        gen_random_uuid(),
        'user_' || i || '_' || (random() * 1000000)::int || '@example.com'
--        NOW() - (random() * INTERVAL '365 days'),
--        NOW()
    FROM generate_series(1, row_count) s(i);

    RAISE NOTICE 'Users inserted';

    -- 2. Генерация данных для USER_PROFILES
    -- Каждый профиль привязан к существующему user.id (связь 1:1)
    INSERT INTO user_profiles (id, first_name, last_name, middle_name, date_of_birth)
    SELECT
        id,
        (ARRAY['Ivan', 'Dmitry', 'Alexey', 'Petr', 'Mikhail', 'Sergey', 'Elena', 'Anna', 'Maria', 'Olga'])[floor(random() * 10 + 1)],
        (ARRAY['Ivanov', 'Petrov', 'Sidorov', 'Kuznetsov', 'Popov', 'Smirnov', 'Volkov', 'Fedorov'])[floor(random() * 8 + 1)],
        (ARRAY['Alexandrovich', 'Ivanovich', 'Petrovich', 'Sergeevich', 'Dmitrievich', NULL])[floor(random() * 6 + 1)],
        '1970-01-01'::date + (random() * INTERVAL '40 years')
--        created_at,
--        NOW()
    FROM users;

    RAISE NOTICE 'Profiles inserted';

    -- 3. Генерация данных для ACCOUNTS
    -- Создаем по одному аккаунту для каждого пользователя
    INSERT INTO accounts (id, user_id, account_number, currency, balance)
--    INSERT INTO accounts (id, user_id, account_number, currency, balance, created_at, updated_at)
    SELECT
        gen_random_uuid(),
        id,
        'ACC' || lpad(row_number() over ()::text, 20, '0'),
        (ARRAY['USD', 'EUR', 'RUB'])[floor(random() * 3 + 1)],
        (random() * 100000)::numeric(15,2)
--        created_at + INTERVAL '1 hour',
--        NOW()
    FROM users;

    RAISE NOTICE 'Accounts inserted';

    -- 4. Генерация данных для TRANSACTIONS
    -- Нам нужно 500 000 транзакций между случайными аккаунтами
    -- Чтобы не делать медленные JOIN, выберем ID аккаунтов во временную таблицу
    CREATE TEMP TABLE tmp_acc_ids AS SELECT id FROM accounts;

    INSERT INTO transactions (id, sender_account_id, receiver_account_id, amount, description)
--    INSERT INTO transactions (id, sender_account_id, receiver_account_id, amount, description, created_at, updated_at)
    SELECT
        gen_random_uuid(),
        (SELECT id FROM tmp_acc_ids OFFSET floor(random() * row_count) LIMIT 1),
        (SELECT id FROM tmp_acc_ids OFFSET floor(random() * row_count) LIMIT 1),
        (random() * 1000)::numeric(15,2),
        'Transfer ' || i

    FROM generate_series(1, row_count) s(i);

    DROP TABLE tmp_acc_ids;

    RAISE NOTICE 'Transactions inserted';
END $$;

COMMIT;

-- Возвращаем настройки
-- SET session_replication_role = 'origin';

-- Сбор статистики для оптимизатора
ANALYZE users;
ANALYZE user_profiles;
ANALYZE accounts;
ANALYZE transactions;