import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders  } from '@angular/common/http';
import { first, Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private httpClient: HttpClient) { }

  getEmployees(): Observable<any>{
    return this.httpClient.get<Employee[]>('http://localhost:8080/api/employees');
  }

  deleteEmployee(id:number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/api/employee/${id}`,
                                                  {headers:new HttpHeaders({
                                                    'Content-Type':'application/json'
                                                  })});
  }

  updateEmployee(id: number, value: any): Observable<Object> {  
    return this.httpClient.put(`http://localhost:8080/api/employee/${id}`, value);  
  }
}
