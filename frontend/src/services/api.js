const API_BASE_URL = 'http://localhost:8080/api'; // Базовый URL вашего API

// Получение всех залов
export const fetchHalls = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/hall`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Не удалось загрузить залы: ${response.status} ${errorText}`);
        }
        return response.json();
    } catch (error) {
        console.error("API Error (fetchHalls):", error);
        throw error;
    }
};

// Получение всех групп
export const fetchGroups = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/group`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Не удалось загрузить группы: ${response.status} ${errorText}`);
        }
        return response.json();
    } catch (error) {
        console.error("API Error (fetchGroups):", error);
        throw error;
    }
};

// Получение всех элементов расписания
// src/services/api.js
export const fetchScheduleItems = async () => {
    const url = `${API_BASE_URL}/schedule_item`;
    console.log(`[API] Запрос элементов расписания с: ${url}`);
    try {
        const response = await fetch(url);
        console.log(`[API] Статус ответа для ${url}: ${response.status}`);
        if (!response.ok) {
            const errorText = await response.text().catch(() => "Не удалось прочитать тело ошибки");
            console.error(`[API] Ошибка для ${url}: ${response.status} - ${errorText}`);
            throw new Error(`Не удалось загрузить расписание: ${response.status} - ${errorText}`);
        }
        const data = await response.json();
        console.log("[API] Получены данные расписания:", data); // <--- ВАЖНЫЙ ЛОГ
        return data;
    } catch (error) {
        console.error(`[API] Ошибка в fetchScheduleItems:`, error);
        if (error.message.includes("Failed to fetch")) {
            throw new Error("Ошибка сети или CORS: Не удалось выполнить запрос к серверу за расписанием.");
        }
        throw error;
    }
};

// Добавление нового элемента расписания
export const addScheduleItem = async (scheduleItemData) => {
    // scheduleItemData должен быть объектом вида:
    // { hallId: number, groupId: number, dayOfWeek: string, startTime: string, endTime: string }
    try {
        const response = await fetch(`${API_BASE_URL}/schedule_item`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(scheduleItemData),
        });
        if (!response.ok) {
            // Попытка прочитать тело ошибки, если оно есть
            const errorBody = await response.json().catch(() => ({ message: "Ошибка сервера при добавлении занятия" }));
            console.error("API Error (addScheduleItem - response not ok):", response.status, errorBody);
            throw new Error(errorBody.message || `Не удалось добавить занятие: ${response.status}`);
        }
        return response.json(); // Возвращаем созданный объект
    } catch (error) {
        console.error("API Error (addScheduleItem - catch):", error);
        throw error; // Перебрасываем ошибку для обработки в компоненте
    }
};

// Удаление элемента расписания по ID
export const deleteScheduleItem = async (itemId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/schedule_item/${itemId}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            // Если бэкенд возвращает тело при ошибке DELETE
            // const errorBody = await response.json().catch(() => ({ message: "Ошибка сервера при удалении занятия" }));
            // console.error("API Error (deleteScheduleItem - response not ok):", response.status, errorBody);
            // throw new Error(errorBody.message || `Не удалось удалить занятие: ${response.status}`);

            // Если тело не возвращается или не в JSON
            const errorText = await response.text();
            console.error("API Error (deleteScheduleItem - response not ok):", response.status, errorText);
            throw new Error(`Не удалось удалить занятие: ${response.status} ${errorText || ''}`);
        }
        // DELETE запросы часто возвращают 204 No Content, поэтому response.json() может вызвать ошибку
        // Достаточно проверить, что статус ОК (200, 204)
        if (response.status === 204 || response.status === 200) {
            return { success: true, message: "Занятие успешно удалено" };
        }
        // На всякий случай, если вернулся какой-то другой успешный статус с телом
        try {
            return await response.json();
        } catch (e) {
            return { success: true, message: "Занятие успешно удалено (без ответа от сервера)" };
        }

    } catch (error) {
        console.error("API Error (deleteScheduleItem - catch):", error);
        throw error;
    }
};