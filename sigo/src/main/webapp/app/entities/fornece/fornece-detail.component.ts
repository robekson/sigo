import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFornece } from 'app/shared/model/fornece.model';

@Component({
  selector: 'jhi-fornece-detail',
  templateUrl: './fornece-detail.component.html',
})
export class ForneceDetailComponent implements OnInit {
  fornece: IFornece | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornece }) => (this.fornece = fornece));
  }

  previousState(): void {
    window.history.back();
  }
}
