package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedClassroom;
import seedu.address.storage.adapter.XmlAdaptedClassroomAttendance;

/**
 * This classroom manager stores classrooms for Trajectory.
 */
public class ClassroomManager {
    private static final Logger logger = LogsCenter.getLogger(ClassroomManager.class);

    private static ClassroomManager classroomManager = null;
    private ArrayList<Classroom> classroomList = new ArrayList<>();

    private ClassroomManager() {
        readClassroomList();
    }

    public static ClassroomManager getInstance() {
        if (classroomManager == null) {
            classroomManager = new ClassroomManager();
        }
        return classroomManager;
    }

    /**
     * Adds a new classroomList to the in-memory array list
     */
    public void addClassroom(Classroom classroom) {
        classroomList.add(classroom);
    }

    /**
     * Finds a classroom from classroomList
     */
    public Classroom findClassroom(String className, String moduleCode) {
        for (Classroom classroom : classroomList) {
            if (classroom.getClassName().getValue().equalsIgnoreCase(className)
                    && classroom.getModuleCode().moduleCode.equalsIgnoreCase(moduleCode)) {
                return classroom;
            }
        }
        return null;
    }

    /**
     * Search for duplication of student assigned to classroom
     */
    public boolean isDuplicateClassroomStudent(Classroom classroom, String matricNo) {
        if (!classroom.getStudents().isEmpty()) {
            return classroom.getStudents().contains(matricNo);
        }
        return false;
    }

    /**
     * Search for duplication of student attendance marked for classroom
     */
    public boolean isDuplicateClassroomStudentAttendance(Classroom classroom, String matricNo, String date) {
        return classroom.getAttendanceList()
                .stream()
                .anyMatch(attendance -> attendance.getDate().equalsIgnoreCase(date)
                            && attendance.getStudentsPresent().contains(matricNo));
    }

    /**
     * Removes a classroom from classroomList
     */
    public void deleteClassroom(Classroom classroom) {
        classroomList.remove(classroom);
    }

    /**
     * Gets the classroom list from storage and converts it to a Classroom array list
     */
    private void readClassroomList() {
        ArrayList<XmlAdaptedClassroom> xmlClassroomList = StorageController.getClassesStorage();
        ArrayList<XmlAdaptedClassroomAttendance> xmlClassroomAttendanceList =
                StorageController.getClassAttendanceStorage();

        for (XmlAdaptedClassroom xmlClassroom : xmlClassroomList) {
            try {
                Classroom classroom = xmlClassroom.toModelType();
                classroomList.add(classroom);
                readAttendanceList(xmlClassroomAttendanceList, classroom);
            } catch (IllegalValueException e) {
                logger.info("Illegal values found when reading classroom list: " + e.getMessage());
            }
        }
    }

    /**
     * Reads back the classroom attendance and store into the classroom attendance list in-memory
     */
    private void readAttendanceList(ArrayList<XmlAdaptedClassroomAttendance> xmlClassroomAttendanceList,
                                    Classroom classroom) throws IllegalValueException {
        if (xmlClassroomAttendanceList.size() == 0) {
            for (Classroom c : classroomList) {
                c.getAttendanceList().add(new Attendance());
            }
        }
        for (XmlAdaptedClassroomAttendance xmlClassroomAttendance : xmlClassroomAttendanceList) {
            if (xmlClassroomAttendance.getClassName().equalsIgnoreCase(classroom.getClassName().getValue())
                    && xmlClassroomAttendance.getModuleCode().equalsIgnoreCase(
                    classroom.getModuleCode().moduleCode)) {
                Attendance attendance = xmlClassroomAttendance.toModelType();
                classroom.getAttendanceList().add(attendance);
            }
        }
    }

    /**
     * Converts the classroom array list and invokes the StorageController to save the current classroom list to file
     */
    public void saveClassroomList() {
        ArrayList<XmlAdaptedClassroom> xmlClassroomList =
                classroomList
                        .stream()
                        .map(XmlAdaptedClassroom::new)
                        .collect(Collectors.toCollection(ArrayList::new));
        StorageController.setClassesStorage(xmlClassroomList);
        StorageController.storeData();
    }

    public ArrayList<Classroom> getClassroomList() {
        return this.classroomList;
    }

    public void setClassroomList(ArrayList<Classroom> classroomList) {
        this.classroomList = classroomList;
    }

    /**
     * Returns true if a classroom with the same identity as {@code classToCreate} exists in the classroom list.
     */
    public boolean hasClassroom(Classroom classToCreate) {
        requireNonNull(classToCreate);
        return (findClassroom(classToCreate.getClassName().getValue(),
                classToCreate.getModuleCode().moduleCode)) != null;
    }

    /**
     * Replaces the classroom {@code classtoEdit} in the list with {@code editedClass}.
     */
    public void updateClassroom(Classroom classtoEdit, Classroom editedClass) {
        int index = classroomList.indexOf(classtoEdit);
        classroomList.set(index, editedClass);
    }

    /**
     * Assigns a student by matricNo to the class
     */
    public void assignStudent(Classroom classToAssignStudent, String matricNo) {
        classToAssignStudent.getStudents().add(matricNo);
    }

    /**
     * Unassigns a student by matricNo from the class
     */
    public void unassignStudent(Classroom classToUnassignStudent, String matricNo) {
        classToUnassignStudent.getStudents().remove(matricNo);
    }

    /**
     * Checks if there exist this student matricNo from the classroom
     */
    public boolean hasClassroomStudent(Classroom classToUnassignStudent, String matricNo) {
        return (classToUnassignStudent.getStudents().contains(matricNo));
    }

    /**
     * Marks the attendance for a student for the class in the given day.
     * If an attendance is available for the class, append the student to the attendance, otherwise
     * add the attendance to the class attendance list.
     */
    public void markStudentAttendance(Classroom classToMarkAttendance, Attendance attendance, String matricNo) {
        attendance.getStudentsPresent().add(matricNo);
        for (Attendance attend : classToMarkAttendance.getAttendanceList()) {
            if (attend.getDate().equalsIgnoreCase(attendance.getDate())) {
                int index = classToMarkAttendance.getAttendanceList().indexOf(attend);
                classToMarkAttendance.getAttendanceList().set(index, attendance);
                return;
            }
        }
        classToMarkAttendance.getAttendanceList().add(attendance);
    }

    /**
     * Returns the attendance for a classroom for the given date
     */
    public Attendance findAttendanceForClass(Classroom classroom, String date) {
        for (Attendance attendance : classroom.getAttendanceList()) {
            if (attendance.getDate().equalsIgnoreCase(date)) {
                return attendance;
            }
        }
        return new Attendance();
    }

    /**
     * Saves the classroom attendance list
     */
    public void saveClassroomAttendanceList() {
        ArrayList<XmlAdaptedClassroomAttendance> xmlClassroomAttendanceList = new ArrayList<>();
        for (Classroom classroom : classroomList) {
            xmlClassroomAttendanceList.addAll(
                    classroom.getAttendanceList()
                            .stream()
                            .map(attendance -> new XmlAdaptedClassroomAttendance(
                                    classroom.getClassName().getValue(),
                                    classroom.getModuleCode().moduleCode,
                                    attendance.getDate(), attendance.getStudentsPresent()))
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        }
        StorageController.setClassAttendanceStorage(xmlClassroomAttendanceList);
        StorageController.storeData();
    }

    /**
     * Returns whether a specified student is from the specified class
     */
    public boolean isStudentFromClass(Classroom classToMarkAttendance, String matricNo) {
        return classToMarkAttendance.getStudents().contains(matricNo);
    }

    /**
     * Modifies the classroom attendance for the specified student
     * if student is marked present, mark them absent
     */
    public void modifyStudentAttendance(Classroom classToMarkAttendance, Attendance attendance, String matricNo) {
        ArrayList<Attendance> attendanceList = classToMarkAttendance.getAttendanceList();
        int index = attendanceList.indexOf(attendance);

        ArrayList<String> studentPresents = attendanceList.get(index).getStudentsPresent();
        studentPresents.remove(matricNo);
        classToMarkAttendance.setAttendanceList(attendanceList);
    }

    /**
     * Returns whether a student's attendance is marked
     */
    public boolean isStudentAttendanceMarked(Attendance attendance, String matricNo) {
        return attendance.getStudentsPresent().contains(matricNo);
    }
}
