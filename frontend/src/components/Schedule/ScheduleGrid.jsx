// src/components/Schedule/ScheduleGrid.jsx
import React from 'react';
import './../../styles/ScheduleGrid.css'; // Убедитесь, что путь к стилям правильный

export default function ScheduleGrid({ schedule, onSlotClick, onItemClick }) {
    console.log("[ScheduleGrid] Получен пропс schedule:", schedule);

    // Для отображения заголовков колонок (русские) и для итерации
    const displayDaysConfig = [
        { key: "Monday", label: "ПН" },
        { key: "Tuesday", label: "ВТ" },
        { key: "Wednesday", label: "СР" },
        { key: "Thursday", label: "ЧТ" },
        { key: "Friday", label: "ПТ" },
        { key: "Saturday", label: "СБ" },
        { key: "Sunday", label: "ВС" },
    ];

    // Генерируем временные слоты для строк таблицы (каждый час)
    const timeSlots = [];
    for (let hour = 8; hour <= 22; hour++) {
        timeSlots.push(`${hour < 10 ? '0' : ''}${hour}:00`); // Формат HH:00
    }

    // Функция для проверки, попадает ли текущий временной слот (timeCell)
    // в интервал занятия (itemStartTime, itemEndTime)
    const isTimeSlotOccupied = (timeCell, itemStartTime, itemEndTime) => {
        // Приводим все к формату "HH:mm" для сравнения
        const cellStart = timeCell; // "08:00"
        const cellEndHour = parseInt(timeCell.substring(0, 2)) + 1;
        const cellEnd = `${cellEndHour < 10 ? '0' : ''}${cellEndHour}:00`; // "09:00"

        const itemStart = itemStartTime.substring(0, 5); // "19:00"
        const itemEnd = itemEndTime.substring(0, 5);     // "20:00"

        // Логика: занятие перекрывает текущий слот, если:
        // начало занятия <= начало слота И конец занятия > начало слота
        // ИЛИ
        // начало занятия < конец слота И конец занятия >= конец слота
        // Проще: слот занят, если начало занятия < конец слота И конец занятия > начало слота
        return itemStart < cellEnd && itemEnd > cellStart;
    };


    return (
        <div className="schedule-container">
            <table className="schedule-table">
                <thead>
                <tr>
                    <th className="time-header">Время</th>
                    {displayDaysConfig.map(dayConfig => (
                        <th key={dayConfig.key}>{dayConfig.label}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {timeSlots.map(timeCell => ( // timeCell - это "08:00", "09:00" и т.д.
                    <tr key={timeCell}>
                        <td className="time-cell">{timeCell}</td>
                        {displayDaysConfig.map(dayConfig => { // dayConfig.key - это "Monday", "Tuesday", ...
                            // Ищем элемент расписания, который соответствует текущему дню и времени
                            const item = schedule.find(scheduleItem =>
                                scheduleItem.dayOfWeek === dayConfig.key &&
                                isTimeSlotOccupied(timeCell, scheduleItem.startTime, scheduleItem.endTime)
                            );

                            // Отладочный лог для конкретной ячейки, если нужно
                            // if (dayConfig.key === 'Sunday' && timeCell === '19:00') {
                            //    console.log(`Проверка для ${dayConfig.label} ${timeCell}:`, item, schedule);
                            // }

                            return (
                                <td
                                    key={`${dayConfig.key}-${timeCell}`}
                                    className={`day-cell ${item ? 'occupied' : 'empty'}`}
                                    onClick={() => item ? onItemClick(item) : onSlotClick(dayConfig.key, timeCell)} // Передаем dayConfig.key для onSlotClick
                                >
                                    {item && (
                                        <div className="schedule-item">
                                            <div className="group-name">
                                                {item.group?.name || 'N/A Группа'}
                                            </div>
                                            {/* Можно добавить имя тренера, если оно есть в item.group.trainer */}
                                            {item.group?.trainer && (
                                                <div className="trainer-name">
                                                    {item.group.trainer.name || 'N/A Тренер'}
                                                </div>
                                            )}
                                            {/* Отображение точного времени занятия, если нужно */}
                                            {/*
                                            <div className="item-time">
                                                {item.startTime.substring(0,5)} - {item.endTime.substring(0,5)}
                                            </div>
                                            */}
                                        </div>
                                    )}
                                </td>
                            );
                        })}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}