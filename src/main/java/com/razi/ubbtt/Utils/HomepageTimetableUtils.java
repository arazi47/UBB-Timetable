package com.razi.ubbtt.Utils;

import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Note;
import com.razi.ubbtt.repositories.CourseRepository;
import com.razi.ubbtt.repositories.NoteRepository;
import com.razi.ubbtt.repositories.WeekRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomepageTimetableUtils {
    CourseRepository courseRepository;
    NoteRepository noteRepository;
    WeekRepository weekRepository;

    public HomepageTimetableUtils(CourseRepository courseRepository, NoteRepository noteRepository, WeekRepository weekRepository) {
        this.courseRepository = courseRepository;
        this.noteRepository = noteRepository;
        this.weekRepository = weekRepository;
    }

    private Map<Course, Note> generateCourseNoteMapForGroup(String group) {
        List<String> groupAndSubgroupsAndYear = GroupUtils.getGroupAndSubgroupsAndYearAsList(group);
        // LinkedHashMap retains the order of insertion
        Map<Course, Note> courseNoteMap = new LinkedHashMap<>();
        for (Course c: ClassUtils.sortCourses(courseRepository.getCoursesForGroupForCurrentSemesterAndWeek(group, groupAndSubgroupsAndYear.get(1), groupAndSubgroupsAndYear.get(2), groupAndSubgroupsAndYear.get(3), weekRepository.getCurrentSemester()))) {
            boolean courseAdded = false;
            for (Note n: noteRepository.getNotesForCurrentSemesterAndWeekAndGroupOrYear(weekRepository.getCurrentSemester(), weekRepository.getCurrentWeek().getWeekNumber(), new ArrayList<>(GroupUtils.getGroupAndSubgroupsAndYearAsList(group)))) {
                if (c.getDiscipline().equals(n.getDiscipline()) && c.getGroupOrYear().equals(n.getGroupOrYear()) && c.getType().equals(n.getCourseType())) {
                    courseNoteMap.put(c, n);
                    if (!courseAdded)
                        courseAdded = true;
                }
            }

            if (!courseAdded)
                courseNoteMap.put(c, null);
        }

        return courseNoteMap;
    }   

    public List<Map<Course, Note>> generateAllCourseNoteMaps() {
        List<Map<Course, Note>> courseNoteMapList = new ArrayList<>();
        courseNoteMapList.add(generateCourseNoteMapForGroup("912"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("914"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("921"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("926"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("935"));
        courseNoteMapList.add(generateCourseNoteMapForGroup("937"));

        // Translate classes from Romanian to English
        for (Map<Course, Note> courseNoteMap: courseNoteMapList) {
            for (Map.Entry<Course, Note> entry : courseNoteMap.entrySet()) {
                entry.getKey().setDay(ClassUtils.dayFromRoToEn(entry.getKey().getDay()));
                entry.getKey().setType(ClassUtils.typeFromRoToEn(entry.getKey().getType()));
                entry.getKey().setDiscipline(ClassUtils.disciplineFromRoToEn(entry.getKey().getDiscipline()));
                entry.getKey().setFrequency(ClassUtils.frequencyFromRoToEn(entry.getKey().getFrequency()));
            }
        }

        return courseNoteMapList;
    }
}
