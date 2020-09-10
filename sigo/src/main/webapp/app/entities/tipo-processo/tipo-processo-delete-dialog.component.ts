import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoProcesso } from 'app/shared/model/tipo-processo.model';
import { TipoProcessoService } from './tipo-processo.service';

@Component({
  templateUrl: './tipo-processo-delete-dialog.component.html',
})
export class TipoProcessoDeleteDialogComponent {
  tipoProcesso?: ITipoProcesso;

  constructor(
    protected tipoProcessoService: TipoProcessoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoProcessoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoProcessoListModification');
      this.activeModal.close();
    });
  }
}
