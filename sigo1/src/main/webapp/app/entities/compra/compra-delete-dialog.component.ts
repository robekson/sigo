import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';

@Component({
  templateUrl: './compra-delete-dialog.component.html',
})
export class CompraDeleteDialogComponent {
  compra?: ICompra;

  constructor(protected compraService: CompraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.compraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('compraListModification');
      this.activeModal.close();
    });
  }
}
