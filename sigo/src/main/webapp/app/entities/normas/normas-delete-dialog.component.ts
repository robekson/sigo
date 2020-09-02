import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INormas } from 'app/shared/model/normas.model';
import { NormasService } from './normas.service';

@Component({
  templateUrl: './normas-delete-dialog.component.html',
})
export class NormasDeleteDialogComponent {
  normas?: INormas;

  constructor(protected normasService: NormasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.normasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('normasListModification');
      this.activeModal.close();
    });
  }
}
