import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { FuncionarioComponent } from './funcionario.component';
import { FuncionarioDetailComponent } from './funcionario-detail.component';
import { FuncionarioUpdateComponent } from './funcionario-update.component';
import { FuncionarioDeleteDialogComponent } from './funcionario-delete-dialog.component';
import { funcionarioRoute } from './funcionario.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(funcionarioRoute)],
  declarations: [FuncionarioComponent, FuncionarioDetailComponent, FuncionarioUpdateComponent, FuncionarioDeleteDialogComponent],
  entryComponents: [FuncionarioDeleteDialogComponent],
})
export class SigoFuncionarioModule {}
