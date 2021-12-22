import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs';
import { Student } from '../student/student';
import { StudentService } from '../student/student.service';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  students: Student[] ;

  student: Student;
  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.studentService.getStudentList().subscribe(
      response => this.handleSuccessfulResponse(response)
    );
  }

  handleSuccessfulResponse(response) {
    this.students = response;
  }

  deleteStudent(firstName: String): void{
    console.log("in delete student....");
    this.studentService.deleteStudent(firstName)
        .subscribe(data => {
          this.students = this.students.filter(u => u.firstName!=firstName);
        })
  }

  deleteStudentById(id: number): void{
    console.log("in delete student By Id....");
    this.studentService.deleteStudentById(id)
        .subscribe(data => {
          this.students = this.students.filter(u => u.id!=id);
        })
  };

  viewStudentInfo(id: number): any{
    console.log("in viewStudentInfo method...");
    return this.studentService.getStudent(id)
          .subscribe(data => {
            this.student = data;
          });
  }
}
