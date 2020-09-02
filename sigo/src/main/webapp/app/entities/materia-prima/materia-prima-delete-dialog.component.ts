import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from './materia-prima.service';

@Component({
  templateUrl: './materia-prima-delete-dialog.component.html',
})
export class MateriaPrimaDeleteDialogComponent {
  materiaPrima?: IMateriaPrima;

  constructor(
    protected materiaPrimaService: MateriaPrimaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.materiaPrimaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('materiaPrimaListModification');
      this.activeModal.close();
    });
  }
}
