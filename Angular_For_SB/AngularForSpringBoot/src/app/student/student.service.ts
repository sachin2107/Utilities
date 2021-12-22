import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';
import { Student } from './student';


@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl = 'http://localhost:8080/api/'; 
  
  private httpOptions = {
    headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    })
};

  getStudentList(): Observable<any> {  
    return this.httpClient.get<Student[]>(`${this.baseUrl}`+'students',this.httpOptions);  
  }  
  
  createStudent(student: object): Observable<object> {  
    return this.httpClient.post<Student>(`${this.baseUrl}student`, student);  
  }  
  
  deleteStudent(firstName: String): Observable<any> {  
    return this.httpClient.delete(`${this.baseUrl}deleteStudent/${firstName}`, { responseType: 'text' });  
  }  

  deleteStudentById(id: number): Observable<any> {
    return this.httpClient.delete(`${this.baseUrl}deletestudent/${id}`);
  }
  
  getStudent(id: number): Observable<any> {  
    return this.httpClient.get(`${this.baseUrl}student/${id}`);  
  }  
  
  updateStudent(id: number, value: any): Observable<Object> {  
    return this.httpClient.post(`${this.baseUrl}/update-student/${id}`, value);  
  }  


}
