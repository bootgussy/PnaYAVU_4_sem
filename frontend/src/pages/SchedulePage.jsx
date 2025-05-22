// ./pages/SchedulePage.jsx
import React, { useState, useEffect } from 'react';
import ScheduleGrid from '../components/Schedule/ScheduleGrid'; // Проверьте путь
import '../styles/main.css'; // Общие стили
import '../styles/SchedulePage.css'; // Добавим стили для страницы (создадим этот файл)

const SchedulePage = ({ onAddGroupClick }) => { // onAddGroupClick приходит из App.js
    const [schedule, setSchedule] = useState([]);

    useEffect(() => {
        const loadData = async () => {
            try {
                const data = await fetchSchedule();
                setSchedule(data);
            } catch (error) {
                console.error("Ошибка при загрузке расписания:", error);
            }
        };
        loadData();
    }, []);

    const handleSlotClick = (day, time) => {
        console.log(`SchedulePage: Клик по пустому слоту - День: ${day}, Время: ${time}`);
    };

    const handleItemClick = (item) => {
        console.log('SchedulePage: Клик по существующему занятию (редактирование):', item);
    };

    return (
        <div className="page-container schedule-page-container"> {/* Добавлен класс schedule-page-container */}
            <div className="page-header-controls"> {/* Новый контейнер для заголовка и кнопки */}
                <h1 className="page-title">Расписание занятий</h1>
                {onAddGroupClick && ( // Кнопка отображается, если onAddGroupClick передан
                    <button
                        className="add-group-button page-header-button" // Добавлен класс для специфичных стилей
                        onClick={onAddGroupClick}
                    >
                        Добавить группу
                    </button>
                )}
            </div>
            <ScheduleGrid
                schedule={schedule}
                onSlotClick={handleSlotClick}
                onItemClick={handleItemClick}
                // onAddGroupClick больше не передается в ScheduleGrid,
                // так как кнопка теперь управляется SchedulePage
            />
        </div>
    );
};

export default SchedulePage;