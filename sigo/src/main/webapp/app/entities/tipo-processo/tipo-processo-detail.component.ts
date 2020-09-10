import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoProcesso } from 'app/shared/model/tipo-processo.model';

@Component({
  selector: 'jhi-tipo-processo-detail',
  templateUrl: './tipo-processo-detail.component.html',
})
export class TipoProcessoDetailComponent implements OnInit {
  tipoProcesso: ITipoProcesso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoProcesso }) => (this.tipoProcesso = tipoProcesso));
  }

  previousState(): void {
    window.history.back();
  }
}
