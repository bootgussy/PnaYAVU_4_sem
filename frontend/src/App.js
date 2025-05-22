import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import SchedulePage from './pages/SchedulePage';
import GroupsPage from './pages/GroupsPage';
import AddScheduleModal from './components/Schedule/AddScheduleModal';

// Импортируем необходимые функции API
import { fetchGroups, addScheduleItem, fetchHalls, fetchScheduleItems } from './services/api';

import './styles/main.css';

function App() {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [groups, setGroups] = useState([]);
    const [halls, setHalls] = useState([]); // Состояние для залов
    const [scheduleItems, setScheduleItems] = useState([]); // Состояние для всех элементов расписания
    const [isLoading, setIsLoading] = useState(true); // Состояние загрузки для начальных данных
    const [error, setError] = useState(null); // Состояние для ошибок загрузки

    // Загрузка начальных данных (группы, залы, расписание)
    useEffect(() => {
        const loadInitialData = async () => {
            setIsLoading(true);
            setError(null);
            try {
                // Параллельная загрузка данных
                const [groupsData, hallsData, scheduleItemsData] = await Promise.all([
                    fetchGroups(),
                    fetchHalls(),
                    fetchScheduleItems()
                ]);
                setGroups(groupsData);
                setHalls(hallsData);
                setScheduleItems(scheduleItemsData);
            } catch (err) {
                console.error("Ошибка при загрузке начальных данных:", err);
                setError(err.message || "Не удалось загрузить данные");
            } finally {
                setIsLoading(false);
            }
        };
        loadInitialData();
    }, []); // Пустой массив зависимостей - выполнится один раз при монтировании

    const handleOpenAddScheduleModal = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleSaveSchedule = async (newScheduleData) => {
        console.log("App.js: Пытаемся сохранить новое расписание:", newScheduleData);
        // Убедимся, что время включает секунды, если бэкенд их ожидает
        const dataToSend = {
            ...newScheduleData,
            startTime: newScheduleData.startTime.includes(':00:') ? newScheduleData.startTime : `${newScheduleData.startTime}:00`,
            endTime: newScheduleData.endTime.includes(':00:') ? newScheduleData.endTime : `${newScheduleData.endTime}:00`,
        };

        try {
            const addedItem = await addScheduleItem(dataToSend);
            setScheduleItems(prevItems => [...prevItems, addedItem]); // Обновляем состояние расписания новым элементом
            alert("Занятие успешно добавлено!");
            handleCloseModal();
        } catch (error) {
            console.error("App.js: Ошибка при сохранении расписания:", error);
            alert(`Ошибка сохранения: ${error.message || "Неизвестная ошибка"}`);
            // Модальное окно остается открытым
        }
    };

    // Функция для обновления расписания после удаления (будет передана в SchedulePage)
    const refreshScheduleItems = async () => {
        setIsLoading(true);
        try {
            const updatedScheduleItems = await fetchScheduleItems();
            setScheduleItems(updatedScheduleItems);
        } catch (err) {
            console.error("Ошибка при обновлении расписания:", err);
            setError(err.message || "Не удалось обновить расписание");
        } finally {
            setIsLoading(false);
        }
    };


    return (
        <Router>
            <Navbar />
            <div className="container mt-4">
                {isLoading && <p>Загрузка данных...</p>}
                {error && <p style={{ color: 'red' }}>Ошибка: {error}</p>}
                {!isLoading && !error && (
                    <Routes>
                        <Route
                            path="/"
                            element={
                                <SchedulePage
                                    halls={halls}
                                    scheduleItems={scheduleItems}
                                    onAddGroupClick={handleOpenAddScheduleModal}
                                    onScheduleUpdateNeeded={refreshScheduleItems} // Передаем функцию обновления
                                />
                            }
                        />
                        <Route
                            path="/schedule"
                            element={
                                <SchedulePage
                                    halls={halls}
                                    scheduleItems={scheduleItems}
                                    onAddGroupClick={handleOpenAddScheduleModal}
                                    onScheduleUpdateNeeded={refreshScheduleItems} // Передаем функцию обновления
                                />
                            }
                        />
                        <Route path="/groups" element={<GroupsPage />} />
                    </Routes>
                )}
            </div>

            <AddScheduleModal
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                onSave={handleSaveSchedule}
                groups={groups}
                halls={halls} // Передаем залы в модальное окно
            />
        </Router>
    );
}

export default App;