import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { ProcessoDetailComponent } from 'app/entities/processo/processo-detail.component';
import { Processo } from 'app/shared/model/processo.model';

describe('Component Tests', () => {
  describe('Processo Management Detail Component', () => {
    let comp: ProcessoDetailComponent;
    let fixture: ComponentFixture<ProcessoDetailComponent>;
    const route = ({ data: of({ processo: new Processo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ProcessoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProcessoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load processo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
