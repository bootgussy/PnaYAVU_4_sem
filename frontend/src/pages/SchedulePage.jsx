// src/pages/SchedulePage.jsx
import React, { useState, useEffect } from 'react';
import ScheduleGrid from '../components/Schedule/ScheduleGrid';
import '../styles/SchedulePage.css';

const SchedulePage = ({
                          halls,
                          groups,
                          scheduleItems,
                          isLoadingAppLevelData, // Получаем флаг из App
                          onAddSlotClick,
                          onViewItemClick,
                          onAddNewScheduleItemClick
                      }) => {
    const [selectedHallId, setSelectedHallId] = useState(null);

    useEffect(() => {
        if (!isLoadingAppLevelData && halls && halls.length > 0 && !selectedHallId) {
            setSelectedHallId(halls[0].id);
        }
    }, [halls, selectedHallId, isLoadingAppLevelData]);

    // Если данные уровня приложения еще грузятся, или залы не загружены
    if (isLoadingAppLevelData && (!halls || halls.length === 0)) {
        return (
            <div className="schedule-page-container">
                <div className="page-header-controls">
                    <h1 className="page-title">Расписание занятий</h1>
                    {onAddNewScheduleItemClick && (<button className="page-header-button" onClick={() => { console.log("SchedulePage: 'Добавить занятие' button clicked (during load)"); onAddNewScheduleItemClick();}}>Добавить занятие</button>)}
                </div>
                <p>Загрузка данных залов...</p>
            </div>
        );
    }

    // Если загрузка завершена, но залов все равно нет
    if (!isLoadingAppLevelData && (!halls || halls.length === 0)) {
        return (
            <div className="schedule-page-container">
                <div className="page-header-controls">
                    <h1 className="page-title">Расписание занятий</h1>
                    {onAddNewScheduleItemClick && (<button className="page-header-button" onClick={() => { console.log("SchedulePage: 'Добавить занятие' button clicked (no halls)"); onAddNewScheduleItemClick();}}>Добавить занятие</button>)}
                </div>
                <p>Залы не загружены или отсутствуют. Расписание не может быть отображено.</p>
            </div>
        );
    }

    const selectedHall = halls.find(h => h.id === selectedHallId);
    const itemsForSelectedHall = scheduleItems.filter(item => item.hall?.id === selectedHallId || item.hallId === selectedHallId);

    const handleGridSlotClick = (dayKey, timeSlot) => {
        console.log("SchedulePage: handleGridSlotClick called", { dayKey, timeSlot, selectedHallId });
        if (selectedHallId && onAddSlotClick) {
            const initialDataForModal = { dayOfWeek: dayKey, time: timeSlot, hallId: selectedHallId };
            console.log("SchedulePage: Calling onAddSlotClick with", initialDataForModal);
            onAddSlotClick(initialDataForModal);
        } else {
            console.warn("SchedulePage: onAddSlotClick not called. selectedHallId:", selectedHallId, "onAddSlotClick exists:", !!onAddSlotClick);
        }
    };

    return (
        <div className="schedule-page-container">
            <div className="page-header-controls">
                <h1 className="page-title">Расписание занятий</h1>
                {onAddNewScheduleItemClick && (
                    <button
                        className="page-header-button"
                        onClick={() => {
                            console.log("SchedulePage: 'Добавить занятие' button clicked");
                            onAddNewScheduleItemClick();
                        }}
                    >
                        Добавить занятие
                    </button>
                )}
            </div>

            <div className="hall-selector-wrapper">
                <div className="hall-selector">
                    {halls.map(hall => (
                        <button
                            key={hall.id}
                            onClick={() => setSelectedHallId(hall.id)}
                            className={`hall-button ${selectedHallId === hall.id ? 'active' : ''}`}
                        >
                            {hall.name}
                        </button>
                    ))}
                </div>
            </div>

            {selectedHall ? (
                <ScheduleGrid
                    hall={selectedHall}
                    groups={groups}
                    scheduleItems={itemsForSelectedHall}
                    onSlotClick={handleGridSlotClick}
                    onItemClick={(item) => {
                        console.log("SchedulePage: onItemClick in ScheduleGrid triggered with item:", item);
                        if (onViewItemClick) {
                            onViewItemClick(item);
                        } else {
                            console.warn("SchedulePage: onViewItemClick is not defined!");
                        }
                    }}
                />
            ) : (
                halls && halls.length > 0 && <p>Выберите зал для отображения расписания.</p>
            )}
        </div>
    );
};

export default SchedulePage;