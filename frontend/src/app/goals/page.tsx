"use client"; // Necesario en App Router para componentes con estado

import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

export default function CalendarPage() {
  return (
    <div style={{ padding: 20 }}>
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin]}
        initialView="dayGridMonth"
        editable={true}
        selectable={true}
        events={[
          { title: "Evento 1", date: "2025-11-01" },
          { title: "Evento 2", date: "2025-11-10" },
        ]}
      />
    </div>
  );
}
