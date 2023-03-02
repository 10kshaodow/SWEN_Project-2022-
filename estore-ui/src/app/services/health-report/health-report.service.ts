import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of } from 'rxjs';
import { HealthReport } from 'src/app/types/HealthReport';

@Injectable({
  providedIn: 'root',
})
export class HealthReportService {
  private apiUrl = 'http://localhost:8080/health-reports';

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

    /**
   * Send a DELETE request to the api url
   * @param healthReportID of type number used apart of calling url
   *
   * @return Observable of the deleted HealthReport
   */
     deleteHealthReport(healthReportID: number): Observable<HealthReport> {
      let url = `${this.apiUrl}/${healthReportID}`;
      return this.http.delete<HealthReport>(url, this.httpOptions).pipe(
        tap((item) => {
          console.log(`API deleted ${healthReportID} healthReport`);
        }),
        catchError(this.handleError<HealthReport>(`deleteHealthReport`))
      );
    }
  
    /**
     * Get all healthReports from the api
     *
     * @returns an Observable of healthReport object array
     */
    getAllHealthReports(): Observable<HealthReport[]> {
      return this.http.get<HealthReport[]>(this.apiUrl).pipe(
        tap((array) => {
          console.log(`API returned ${array.length} healthReports`);
        }),
        catchError(this.handleError<HealthReport[]>(`getAllHealthReports`, []))
      );
    }
  
    /**
     * GET a single healthReport from the server
     *
     * @param id of type number used in the calling url
     *
     * @return Observable of the requested HealthReport
     *  */
    getHealthReport(id: number): Observable<HealthReport> {
      const url = `${this.apiUrl}/${id}`;
      return this.http.get<HealthReport>(url).pipe(
        tap((_) => console.log(`fetched healthReport with id of ${id}`)),
        catchError(this.handleError<HealthReport>(`getHealthReport id=${id}`))
      );
    }
  
    /** PUT: update the healthReport on the server
     * @param healthReport
     * @return an observable allowing the caller to wait on receiving the updated healthReport until the healthReport update has completed
     */
    updateHealthReport(healthReport: HealthReport): Observable<any> {
      return this.http.put(this.apiUrl, healthReport, this.httpOptions).pipe(
        tap((_) => console.log(`updated healthReport id=${healthReport.reportID}`)),
        catchError(this.handleError<any>('updateHealthReport'))
      );
    }
  
    /** POST: add a new healthReport to the server
     * @param healthReport - The new healthReport created by the healthReport based on custom data
     * @return an observable allowing the caller to wait on receiving the newly created healthReport until the healthReport creation has completed
     */
    addHealthReport(healthReport: HealthReport): Observable<HealthReport> {
      //if(this.checkHealthReportExists(healthReport.id))
        //throw new HealthReportExistsError(`the id ${healthReport.id} is already taken. HealthReport ids must be unique`); 
        console.log("Adding healthReport: " + JSON.stringify(healthReport));
        return this.http.post<HealthReport>(this.apiUrl, healthReport, this.httpOptions).pipe(
        tap((newHealthReport: HealthReport) =>
          console.log(`added healthReport w/ id=${newHealthReport.reportID}`)
        ),
        catchError(this.handleError<HealthReport>('addHealthReport'))
      );
    }

      /**
   * GET health reports whose falcon ID contains search term
   *
   * @param term type of string used in the calling url
   *
   * @return Observable of an Array of Health Reports that match the terms
   *  */
  searchHealthReports(id: number): Observable<HealthReport[]> {
    let term: string = String(id);
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<HealthReport[]>(`${this.apiUrl}/?searchTerm=${term}`).pipe(
      tap((x) =>
        x.length
          ? console.log(`found health reports matching "${term}"`)
          : console.log(`no reports matching "${term}"`)
      ),
      catchError(this.handleError<HealthReport[]>('searchHealthReports', []))
    );
  }

    /*
      Simple Error handler will log it to browser console
  
      returns an observable of specified type
    */
    private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        console.error(error);
        console.log();
        console.log(`${operation} failed`);
        return of(result as T);
      };
    }
}
