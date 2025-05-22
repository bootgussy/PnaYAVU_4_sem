// src/components/Modal/AddScheduleModal.jsx
import React, { useState, useEffect } from 'react';
import './../../styles/AddScheduleModal.css';

// Принимаем halls как пропс
const AddScheduleModal = ({ isOpen, onClose, onSave, groups = [], halls = [] }) => {
    const [selectedGroupId, setSelectedGroupId] = useState('');
    const [selectedHallId, setSelectedHallId] = useState(''); // Для выбора зала
    const [dayOfWeek, setDayOfWeek] = useState('Monday'); // Используем английские названия, если бэкенд их ожидает
    const [startTime, setStartTime] = useState('08:00');
    const [endTime, setEndTime] = useState('09:00');
    const [error, setError] = useState('');

    // Дни недели (убедитесь, что они соответствуют ожиданиям бэкенда)
    const days = [
        { value: 'Monday', label: 'ПН' },
        { value: 'Tuesday', label: 'ВТ' },
        { value: 'Wednesday', label: 'СР' },
        { value: 'Thursday', label: 'ЧТ' },
        { value: 'Friday', label: 'ПТ' },
        { value: 'Saturday', label: 'СБ' },
        { value: 'Sunday', label: 'ВС' },
    ];

    const timeSlots = [];
    for (let hour = 8; hour <= 22; hour++) {
        const time = `${hour < 10 ? '0' : ''}${hour}:00`;
        timeSlots.push(time);
    }
    // Добавим 23:00 для endTime
    const endTimeSlots = [...timeSlots, '23:00'];

    useEffect(() => {
        if (isOpen) {
            // Сброс формы при открытии
            setSelectedGroupId(groups.length > 0 ? groups[0].id.toString() : '');
            setSelectedHallId(halls.length > 0 ? halls[0].id.toString() : '');
            setDayOfWeek('Monday');
            setStartTime('08:00');
            setEndTime('09:00');
            setError('');
        }
    }, [isOpen, groups, halls]);

    const handleSave = () => {
        setError('');
        if (!selectedGroupId) {
            setError('Пожалуйста, выберите группу.');
            return;
        }
        if (!selectedHallId) {
            setError('Пожалуйста, выберите зал.');
            return;
        }
        if (startTime >= endTime) {
            setError('Время начала должно быть раньше времени окончания.');
            return;
        }

        const scheduleItem = {
            hallId: parseInt(selectedHallId), // Убедимся, что это число
            groupId: parseInt(selectedGroupId), // Убедимся, что это число
            dayOfWeek, // Уже строка (e.g., "Monday")
            startTime, // Уже строка "HH:mm"
            endTime,   // Уже строка "HH:mm"
        };
        onSave(scheduleItem);
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Добавить занятие</h2>
                {error && <p className="modal-error">{error}</p>}
                <div className="form-group">
                    <label htmlFor="hall">Зал:</label>
                    <select
                        id="hall"
                        value={selectedHallId}
                        onChange={(e) => setSelectedHallId(e.target.value)}
                    >
                        <option value="" disabled>Выберите зал</option>
                        {halls.map(hall => (
                            <option key={hall.id} value={hall.id.toString()}>{hall.name}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="group">Группа:</label>
                    <select
                        id="group"
                        value={selectedGroupId}
                        onChange={(e) => setSelectedGroupId(e.target.value)}
                    >
                        <option value="" disabled>Выберите группу</option>
                        {groups.map(group => (
                            <option key={group.id} value={group.id.toString()}>{group.name} ({group.difficulty})</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="dayOfWeekModal">День недели:</label>
                    <select
                        id="dayOfWeekModal"
                        value={dayOfWeek}
                        onChange={(e) => setDayOfWeek(e.target.value)}
                    >
                        {days.map(day => (
                            <option key={day.value} value={day.value}>{day.label}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="startTimeModal">Время начала:</label>
                    <select
                        id="startTimeModal"
                        value={startTime}
                        onChange={(e) => setStartTime(e.target.value)}
                    >
                        {timeSlots.map(time => (
                            <option key={time} value={time}>{time}</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="endTimeModal">Время окончания:</label>
                    <select
                        id="endTimeModal"
                        value={endTime}
                        onChange={(e) => setEndTime(e.target.value)}
                    >
                        {endTimeSlots.filter(time => time > startTime).map(time => (
                            <option key={time} value={time}>{time}</option>
                        ))}
                    </select>
                </div>

                <div className="modal-actions">
                    <button onClick={handleSave} className="modal-button save">Сохранить</button>
                    <button onClick={onClose} className="modal-button cancel">Отмена</button>
                </div>
            </div>
        </div>
    );
};

export default AddScheduleModal;