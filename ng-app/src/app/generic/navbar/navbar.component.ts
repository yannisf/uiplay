import {Component, OnInit} from '@angular/core';
import {AppInfoService, BuildInfo} from "../../app-info.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isCollapsed = false;

  buildInfo: BuildInfo;
  build: string;

  constructor(private appInfoService: AppInfoService) {
  }

  ngOnInit() {
    this.appInfoService.buildInfo().subscribe(buildInfo => this.buildInfo = buildInfo);
    this.build = environment.production?'prod':'dev';
  }

}
