import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const RESOURCE_APPINFO = "api/appinfo";

export class BuildInfo {
  gitHash: string;
  timeStamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class AppInfoService {

  constructor(private http: HttpClient) {
  }

  buildInfo(): Observable<BuildInfo> {
    return this.http.get<BuildInfo>(`${RESOURCE_APPINFO}/build`);
  }

}
